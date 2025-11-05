package com.acme.hms.util;

import com.acme.hms.model.Invoice;
import com.acme.hms.model.InvoiceItem;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;

public final class InvoicePdfGenerator {

    private InvoicePdfGenerator() {
    }

    public static Path generate(Path target, Invoice invoice) throws IOException {
        Document document = new Document();
        try (FileOutputStream fos = new FileOutputStream(target.toFile())) {
            PdfWriter.getInstance(document, fos);
            document.open();
            NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
            document.add(new Paragraph("Invoice #" + invoice.getId(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Patient ID: " + invoice.getPatientId()));
            document.add(new Paragraph("Total: " + currency.format(invoice.getTotal())));
            document.add(new Paragraph("Paid: " + currency.format(invoice.getPaid())));
            document.add(new Paragraph("Payment Method: " + invoice.getMethod()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.addCell(headerCell("Description"));
            table.addCell(headerCell("Qty"));
            table.addCell(headerCell("Unit Price"));
            table.addCell(headerCell("Subtotal"));
            for (InvoiceItem item : invoice.getItems()) {
                table.addCell(new Phrase(item.getDescription()));
                table.addCell(new Phrase(String.valueOf(item.getQuantity())));
                table.addCell(new Phrase(currency.format(item.getUnitPrice())));
                table.addCell(new Phrase(currency.format(item.getUnitPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))));
            }
            document.add(table);
        } catch (DocumentException ex) {
            throw new IOException("Unable to generate invoice PDF", ex);
        } finally {
            document.close();
        }
        return target;
    }

    private static PdfPCell headerCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        cell.setGrayFill(0.9f);
        return cell;
    }
}
