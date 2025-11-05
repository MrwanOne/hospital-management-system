package com.acme.hms.service;

import com.acme.hms.model.Invoice;
import com.acme.hms.model.InvoiceItem;
import java.math.BigDecimal;

public class BillingService {

    public void validateInvoice(Invoice invoice) {
        BigDecimal calculated = invoice.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (calculated.compareTo(invoice.getTotal()) != 0) {
            throw new IllegalStateException("Invoice total does not match line items");
        }
        if (invoice.getPaid().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Paid amount cannot be negative");
        }
        if (invoice.getPaid().compareTo(invoice.getTotal()) > 0) {
            throw new IllegalArgumentException("Paid amount cannot exceed total");
        }
    }

    public BigDecimal outstandingBalance(Invoice invoice) {
        return invoice.getTotal().subtract(invoice.getPaid());
    }

    public void addItem(Invoice invoice, InvoiceItem item) {
        invoice.getItems().add(item);
    }
}
