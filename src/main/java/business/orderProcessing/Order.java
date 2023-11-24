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
    public Order(List<Item> items) {
        this.items = items;
        this.status = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.timestamp = dateFormat.format(new Date());
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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
                // Invoice header
                contentStream.beginText();  // Call beginText() before setting font and placing text
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Invoice");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("Date: " + timestamp);
                contentStream.newLine();
                contentStream.showText("Customer: " + customer);
                contentStream.newLine();
                contentStream.newLine();

                // Invoice items
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.showText("Product ID          Name          Quantity          Price          Total");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                for (Item item : items) {
                    contentStream.showText(item.getProduct().getCode() + "          " + item.getProduct().getName() + "          " + item.getQuantityOrdered() + "          $" + item.getPrice() + "          $" + item.total());
                    contentStream.newLine();
                }

                contentStream.newLine();

                // Total cost
                contentStream.showText("Total Cost: $" + String.format("%.2f", total()));

                contentStream.endText();  // Call endText() when you're done with text operations
            }

            document.save("invoice.pdf");  // Save the PDF to a file

            // Open the generated PDF in the default web browser
            File file = new File("invoice.pdf");
            Desktop desktop = Desktop.getDesktop();

            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(file);
            } else {
                System.out.println("Opening PDF is not supported on this platform.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
