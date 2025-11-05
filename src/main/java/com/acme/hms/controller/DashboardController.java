package com.acme.hms.controller;

import com.acme.hms.service.AppointmentService;
import com.acme.hms.service.InventoryService;
import java.sql.SQLException;

public class DashboardController {

    private final AppointmentService appointmentService;
    private final InventoryService inventoryService;

    public DashboardController(AppointmentService appointmentService, InventoryService inventoryService) {
        this.appointmentService = appointmentService;
        this.inventoryService = inventoryService;
    }

    public int totalAppointments() throws SQLException {
        return appointmentService.listAll().size();
    }

    public int lowStockCount() throws SQLException {
        return inventoryService.lowStockItems().size();
    }
}
