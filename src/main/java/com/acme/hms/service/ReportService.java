package com.acme.hms.service;

import com.acme.hms.model.Patient;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * Generates PDF and CSV reports for key entities.
 */
public class ReportService {

    public void exportInvoiceSummary(Path targetFile, String invoiceNumber, String patientName,
            double total, double paid) throws IOException {
        Document document = new Document();
        try (FileOutputStream outputStream = new FileOutputStream(targetFile.toFile())) {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("Invoice: " + invoiceNumber));
            document.add(new Paragraph("Patient: " + patientName));
            document.add(new Paragraph("Total: " + total));
            document.add(new Paragraph("Paid: " + paid));
            document.add(new Paragraph("Balance: " + (total - paid)));
        } catch (DocumentException exception) {
            throw new IOException("Failed to generate invoice PDF", exception);
        } finally {
            document.close();
        }
    }

    public void exportInventory(Path targetFile, List<String[]> rows) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(
                new OutputStreamWriter(new FileOutputStream(targetFile.toFile()),
                        StandardCharsets.UTF_8),
                CSVFormat.DEFAULT.withHeader("Item", "Quantity", "Price"))) {
            for (String[] row : rows) {
                printer.printRecord((Object[]) row);
            }
        }
    }
}
