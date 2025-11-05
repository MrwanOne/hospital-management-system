package com.acme.hms.service;

import com.acme.hms.dao.InventoryDao;
import com.acme.hms.model.InventoryItem;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryService {

    private final InventoryDao inventoryDao;

    public InventoryService(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    public List<InventoryItem> listAll() throws SQLException {
        return inventoryDao.findAll();
    }

    public long registerItem(InventoryItem item) throws SQLException {
        enforceMinimums(item);
        return inventoryDao.insert(item);
    }

    public int updateItem(InventoryItem item) throws SQLException {
        enforceMinimums(item);
        return inventoryDao.update(item);
    }

    public List<InventoryItem> lowStockItems() throws SQLException {
        return inventoryDao.findAll().stream()
                .filter(item -> item.getQuantity() <= item.getMinQuantity())
                .collect(Collectors.toList());
    }

    private void enforceMinimums(InventoryItem item) {
        if (item.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (item.getMinQuantity() < 0) {
            throw new IllegalArgumentException("Minimum quantity cannot be negative");
        }
        if (item.getQuantity() < item.getMinQuantity()) {
            throw new IllegalStateException("Quantity cannot be below minimum threshold");
        }
    }
}
