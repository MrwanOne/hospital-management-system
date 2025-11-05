package com.acme.hms.util;

import com.acme.hms.model.InventoryItem;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public final class InventoryCsvExporter {

    private InventoryCsvExporter() {
    }

    public static Path export(Path target, List<InventoryItem> items) throws IOException {
        try (Writer writer = Files.newBufferedWriter(target);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("Item Code", "Name", "Quantity", "Unit", "Min Quantity", "Price"))) {
            for (InventoryItem item : items) {
                printer.printRecord(item.getItemCode(), item.getName(), item.getQuantity(), item.getUnit(),
                        item.getMinQuantity(), item.getPrice());
            }
        }
        return target;
    }
}
