package business.orderProcessing;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Order extends ItemContainer {

    private boolean status;
    private String customer;
    private String timestamp;
    private double enteredAmount;
    public Order(List<Item> items) {
        this.items = items;
        this.status = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.timestamp = dateFormat.format(new Date());
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public void setEnteredAmount(double enteredAmount) {
        this.enteredAmount = enteredAmount;
    }

    public void cancel() {
        this.status = false;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
    public void generateInvoice() {
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
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Address: FAST NUCES Lahore");
                contentStream.newLine();
                contentStream.showText("Email: customercare@rjpharmaceuticals.com");
                contentStream.newLine();
                contentStream.showText("Phone No: 03208433967");
                contentStream.newLine();
                contentStream.showText("Customer: " + customer);
                contentStream.newLine();
                contentStream.showText("Timestamp: " + timestamp);
                contentStream.endText();


                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_BOLD, 18);
                contentStream.newLineAtOffset(275, 620);
                contentStream.showText("Receipt");
                contentStream.endText();


                float startY = 550;
                float startX = 50;
                float rowHeight = 20;
                float cellMargin = 5;
                float nameWidth = 150;
                float priceWidth = 100;
                float quantityWidth = 100;
                float totalColWidth = 100;
                float totalWidth = nameWidth + priceWidth + quantityWidth + totalColWidth;


                float titleY = startY - 15;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(startX + cellMargin, titleY);
                contentStream.showText("Name");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(startX + nameWidth + cellMargin, titleY);
                contentStream.showText("Price");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(startX + nameWidth + priceWidth + cellMargin, titleY);
                contentStream.showText("Quantity");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(startX + nameWidth + priceWidth + quantityWidth + cellMargin, titleY);
                contentStream.showText("Total");
                contentStream.endText();


                float nextY = startY;
                for (int i = 0; i <= items.size() + 1; i++) {
                    contentStream.moveTo(startX, nextY);
                    contentStream.lineTo(startX + totalWidth, nextY);
                    contentStream.stroke();

                    nextY -= rowHeight;
                }

                for (float verticalLinePosition : new float[]{startX, startX + nameWidth, startX + nameWidth + priceWidth, startX + nameWidth + priceWidth + quantityWidth, startX + totalWidth}) {
                    contentStream.moveTo(verticalLinePosition, startY);
                    contentStream.lineTo(verticalLinePosition, startY - (items.size() + 1) * rowHeight);
                    contentStream.stroke();
                }

                float textY = startY - rowHeight - 15;
                for (Item item : items) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);

                    contentStream.newLineAtOffset(startX + cellMargin, textY);
                    contentStream.showText(item.getProduct().getName());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + nameWidth + cellMargin, textY);
                    contentStream.showText(String.format("%.2f", item.getPrice()));
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + nameWidth + priceWidth + cellMargin, textY);
                    contentStream.showText(Integer.toString(item.getQuantityOrdered()));
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + nameWidth + priceWidth + quantityWidth + cellMargin, textY);
                    contentStream.showText(String.format("%.2f", item.total()));
                    contentStream.endText();

                    textY -= rowHeight;
                }


                float financialInfoStartY = startY - (items.size() + 1) * rowHeight - 20; // Adjusted for spacing
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(startX, financialInfoStartY);

                double subtotal = total();

                contentStream.showText("Subtotal: $" + String.format("%.2f", subtotal));
                contentStream.newLineAtOffset(0, -15); // Move to the next line

                contentStream.showText("Paid Amount: $" + String.format("%.2f", enteredAmount));
                contentStream.newLineAtOffset(0, -15); // Move to the next line

                double change = enteredAmount - subtotal;
                contentStream.showText("Balance: $" + String.format("%.2f", change));
                contentStream.endText();

                contentStream.close();
                document.save("Receipt.pdf");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (Desktop.isDesktopSupported()) {
                try {
                    File pdfFile = new File("Receipt.pdf");
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
}
