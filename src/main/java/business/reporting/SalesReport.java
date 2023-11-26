package business.reporting;

import business.orderProcessing.Item;
import business.orderProcessing.Order;
import business.productCatalog.Product;
import dao.OrderDAO;
import dao.ProductDAO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SalesReport extends Report {
    private String reportType;
    private List<Order> orders;

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    public void display() {
        OrderDAO orderDAO = new OrderDAO();
        orders = orderDAO.getOrders();
        String currentDate = getCurrentDate();
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_BOLD, 26);
                contentStream.newLineAtOffset(200, 750);
                contentStream.showText("R&J Pharmaceuticals");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_BOLD, 18);
                contentStream.newLineAtOffset(230, 620);
                contentStream.showText(reportType + " Sales Report");
                contentStream.endText();


                float startY = 550;
                float startX = 50;
                float rowHeight = 20;
                float cellMargin = 5;
                float codeWidth = 50;
                float nameWidth = 100;
                float quantityWidth = 100;
                float priceWidth = 50;
                float totalPriceWidth = 70;
                float totalWidth = codeWidth + nameWidth + quantityWidth + priceWidth + totalPriceWidth;

                switch (reportType) {
                    case "Daily":
                        orders.removeIf(order -> !isSameDay(order.getTimestamp(), currentDate));
                        break;
                    case "Monthly":
                        orders.removeIf(order -> !isSameMonth(order.getTimestamp(), currentDate));
                        break;
                    case "Weekly":
                        orders.removeIf(order -> !isWithinLastWeek(order.getTimestamp(), currentDate));
                        break;
                }

                int id = 1;
                for (Order order : orders) {
                    float headingY = startY + 10;
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(startX + cellMargin, headingY);
                    contentStream.showText("Order ID: " + id);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(startX + 70 + cellMargin, headingY);
                    contentStream.showText("Total Amount: " + order.total());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(startX + 200 + cellMargin, headingY);
                    contentStream.showText("Timestamp: " + order.getTimestamp());
                    contentStream.endText();

                    float titleY = startY - 15;

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(startX + cellMargin, titleY);
                    contentStream.showText("Code");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + codeWidth + cellMargin, titleY);
                    contentStream.showText("Name");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + codeWidth + nameWidth + cellMargin, titleY);
                    contentStream.showText("Quantity");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + codeWidth + nameWidth + quantityWidth + cellMargin, titleY);
                    contentStream.showText("Price");
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + codeWidth + nameWidth + quantityWidth + priceWidth + cellMargin, titleY);
                    contentStream.showText("Total:");
                    contentStream.endText();


                    List<Item> items = order.getItemsList();
                    float nextY = startY;
                    for (int i = 0; i <= items.size() + 1; i++) {
                        contentStream.moveTo(startX, nextY);
                        contentStream.lineTo(startX + totalWidth, nextY);
                        contentStream.stroke();

                        nextY -= rowHeight;
                    }

                    for (float verticalLinePosition : new float[]{startX, startX + codeWidth, startX + codeWidth + nameWidth, startX + codeWidth + nameWidth + quantityWidth, startX + codeWidth + nameWidth + quantityWidth + priceWidth, startX + totalWidth}) {
                        contentStream.moveTo(verticalLinePosition, startY);
                        contentStream.lineTo(verticalLinePosition, startY - (items.size() + 1) * rowHeight);
                        contentStream.stroke();
                    }

                    float textY = startY - rowHeight - 15;
                    for (Item item : items) {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);

                        contentStream.newLineAtOffset(startX + cellMargin, textY);
                        contentStream.showText(Integer.toString(item.getProduct().getCode()));
                        contentStream.endText();

                        contentStream.beginText();
                        contentStream.newLineAtOffset(startX + codeWidth + cellMargin, textY);
                        contentStream.showText(item.getProduct().getName());
                        contentStream.endText();

                        contentStream.beginText();
                        contentStream.newLineAtOffset(startX + codeWidth + nameWidth + cellMargin, textY);
                        contentStream.showText(Integer.toString(item.getQuantityOrdered()));
                        contentStream.endText();

                        contentStream.beginText();
                        contentStream.newLineAtOffset(startX + codeWidth + nameWidth + quantityWidth + cellMargin, textY);
                        contentStream.showText(String.format("%.2f", item.getPrice()));
                        contentStream.endText();

                        contentStream.beginText();
                        contentStream.newLineAtOffset(startX + codeWidth + nameWidth + quantityWidth + priceWidth + cellMargin, textY);
                        contentStream.showText(String.format("%.2f", item.total()));
                        contentStream.endText();

                        textY -= rowHeight;
                    }
                    id++;
                    startY = nextY - rowHeight;
                }
                contentStream.close();
                document.save(reportType + " Sales Report.pdf");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (Desktop.isDesktopSupported()) {
                try {
                    File pdfFile = new File(reportType + " Sales Report.pdf");
                    Desktop.getDesktop().open(pdfFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Unable to open the PDF file.");
                }
            } else {
                System.err.println("Desktop is not supported on this platform.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isSameDay(String orderTimestamp, String currentDate) {
        return orderTimestamp.startsWith(currentDate.substring(0,7));
    }

    private boolean isSameMonth(String orderTimestamp, String currentDate) {
        return orderTimestamp.startsWith(currentDate.substring(0, 7));
    }

    private boolean isWithinLastWeek(String orderTimestamp, String currentDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
            Date orderDate = sdf.parse(orderTimestamp);
            Date currentDateObj = sdf.parse(currentDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDateObj);
            calendar.add(Calendar.DAY_OF_MONTH, -7);

            return orderDate.after(calendar.getTime()) && orderDate.before(currentDateObj);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        return sdf.format(new Date());
    }
}