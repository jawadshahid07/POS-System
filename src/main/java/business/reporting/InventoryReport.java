package business.reporting;

import business.orderProcessing.Item;
import business.productCatalog.Product;
import dao.ProductDAO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class InventoryReport extends Report {
    private List<Product> products;

    public void display() {
        ProductDAO productDAO = new ProductDAO();
        products = productDAO.getAllProducts();
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
                contentStream.newLineAtOffset(250, 620);
                contentStream.showText("Inventory Report");
                contentStream.endText();


                float startY = 550;
                float startX = 50;
                float rowHeight = 20;
                float cellMargin = 5;
                float codeWidth = 50;
                float nameWidth = 100;
                float descriptionWidth = 100;
                float quantityWidth = 100;
                float priceWidth = 50;
                float dateWidth = 100;
                float totalWidth = codeWidth + nameWidth + descriptionWidth + quantityWidth + priceWidth + dateWidth;


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
                contentStream.showText("Description");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(startX + codeWidth + nameWidth + descriptionWidth + cellMargin, titleY);
                contentStream.showText("Quantity");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(startX + codeWidth + nameWidth + descriptionWidth + quantityWidth + cellMargin, titleY);
                contentStream.showText("Price");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(startX + codeWidth + nameWidth + descriptionWidth + quantityWidth + priceWidth + cellMargin, titleY);
                contentStream.showText("Expiration Date");
                contentStream.endText();


                float nextY = startY;
                for (int i = 0; i <= products.size() + 1; i++) {
                    contentStream.moveTo(startX, nextY);
                    contentStream.lineTo(startX + totalWidth, nextY);
                    contentStream.stroke();

                    nextY -= rowHeight;
                }

                for (float verticalLinePosition : new float[]{startX, startX + codeWidth, startX + codeWidth + nameWidth, startX + codeWidth + nameWidth + descriptionWidth, startX + codeWidth + nameWidth + descriptionWidth + quantityWidth, startX + codeWidth + nameWidth + descriptionWidth + quantityWidth + priceWidth, startX + totalWidth}) {
                    contentStream.moveTo(verticalLinePosition, startY);
                    contentStream.lineTo(verticalLinePosition, startY - (products.size() + 1) * rowHeight);
                    contentStream.stroke();
                }

                float textY = startY - rowHeight - 15;
                for (Product product : products) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);

                    contentStream.newLineAtOffset(startX + cellMargin, textY);
                    contentStream.showText(Integer.toString(product.getCode()));
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + codeWidth + cellMargin, textY);
                    contentStream.showText(product.getName());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + codeWidth + nameWidth + cellMargin, textY);
                    contentStream.showText(product.getDescription());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + codeWidth + nameWidth + descriptionWidth + cellMargin, textY);
                    contentStream.showText(Integer.toString(product.getStockQuantity()));
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + codeWidth + nameWidth + descriptionWidth + quantityWidth + cellMargin, textY);
                    contentStream.showText(String.format("%.2f", product.getPrice()));
                    contentStream.endText();


                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX + codeWidth + nameWidth + descriptionWidth + quantityWidth + priceWidth + cellMargin, textY);
                    contentStream.showText(product.getExpirationDate());
                    contentStream.endText();

                    textY -= rowHeight;
                }
                contentStream.close();
                document.save("Inventory Report.pdf");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (Desktop.isDesktopSupported()) {
                try {
                    File pdfFile = new File("Inventory Report.pdf");
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
