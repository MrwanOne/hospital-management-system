package com.acme.hms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.acme.hms.dao.AppointmentDao;
import com.acme.hms.model.Appointment;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AppointmentServiceTest {
    private AppointmentDao appointmentDao;
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        appointmentDao = Mockito.mock(AppointmentDao.class);
        appointmentService = new AppointmentService(appointmentDao);
    }

    @Test
    void scheduleRejectsConflicts() throws SQLException {
        Appointment appointment = sampleAppointment();
        when(appointmentDao.hasConflict(any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> appointmentService.schedule(appointment));
    }

    @Test
    void scheduleDelegatesToDao() throws SQLException {
        Appointment appointment = sampleAppointment();
        when(appointmentDao.hasConflict(any())).thenReturn(false);
        when(appointmentDao.create(any())).thenReturn(10L);
        long id = appointmentService.schedule(appointment);
        assertEquals(10L, id);
    }

    @Test
    void listAppointmentsDelegatesToDao() throws SQLException {
        when(appointmentDao.findAll()).thenReturn(List.of(sampleAppointment()));
        assertEquals(1, appointmentService.listAppointments().size());
    }

    private Appointment sampleAppointment() {
        Appointment appointment = new Appointment();
        appointment.setDoctorUserId(1L);
        appointment.setPatientId(2L);
        appointment.setStartAt(LocalDateTime.now().plusHours(1));
        appointment.setEndAt(LocalDateTime.now().plusHours(2));
        appointment.setStatus("SCHEDULED");
        return appointment;
    }
}
