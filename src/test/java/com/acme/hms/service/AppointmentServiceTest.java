package com.acme.hms.service;

import com.acme.hms.dao.AppointmentDao;
import com.acme.hms.model.Appointment;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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
    void scheduleThrowsOnConflict() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setDoctorUserId(1L);
        appointment.setStartAt(LocalDateTime.now());
        appointment.setEndAt(LocalDateTime.now().plusMinutes(30));

        Appointment existing = new Appointment();
        existing.setId(99L);
        Mockito.when(appointmentDao.findByDoctorAndRange(Mockito.eq(1L), Mockito.any(), Mockito.any()))
                .thenReturn(List.of(existing));

        Assertions.assertThrows(IllegalStateException.class, () -> appointmentService.schedule(appointment));
    }

    @Test
    void schedulePersistsWhenNoConflict() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setDoctorUserId(1L);
        appointment.setStartAt(LocalDateTime.now());
        appointment.setEndAt(LocalDateTime.now().plusMinutes(30));

        Mockito.when(appointmentDao.findByDoctorAndRange(Mockito.eq(1L), Mockito.any(), Mockito.any()))
                .thenReturn(List.of());
        Mockito.when(appointmentDao.insert(Mockito.any())).thenReturn(1L);

        long id = appointmentService.schedule(appointment);
        Assertions.assertEquals(1L, id);
    }
}
