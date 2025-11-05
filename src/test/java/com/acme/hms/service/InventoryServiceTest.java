package com.acme.hms.service;

import com.acme.hms.dao.InventoryDao;
import com.acme.hms.model.InventoryItem;
import java.math.BigDecimal;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InventoryServiceTest {

    private InventoryDao inventoryDao;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryDao = Mockito.mock(InventoryDao.class);
        inventoryService = new InventoryService(inventoryDao);
    }

    @Test
    void registerItemRejectsBelowMinimum() {
        InventoryItem item = new InventoryItem();
        item.setQuantity(5);
        item.setMinQuantity(10);
        item.setPrice(BigDecimal.ONE);
        Assertions.assertThrows(IllegalStateException.class, () -> inventoryService.registerItem(item));
    }

    @Test
    void registerItemPersistsWhenValid() throws SQLException {
        InventoryItem item = new InventoryItem();
        item.setQuantity(20);
        item.setMinQuantity(5);
        item.setPrice(BigDecimal.ONE);
        Mockito.when(inventoryDao.insert(Mockito.any())).thenReturn(1L);
        long id = inventoryService.registerItem(item);
        Assertions.assertEquals(1L, id);
    }
}
