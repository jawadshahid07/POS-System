package business.reporting;

import business.orderProcessing.Item;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SalesReport extends Report {
    private String reportType;

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    public void display() {

    }
}