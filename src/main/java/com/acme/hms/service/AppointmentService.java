package com.acme.hms.service;

import com.acme.hms.dao.AppointmentDao;
import com.acme.hms.model.Appointment;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {

    private final AppointmentDao appointmentDao;

    public AppointmentService(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    public long schedule(Appointment appointment) throws SQLException {
        ensureNoConflict(appointment);
        return appointmentDao.insert(appointment);
    }

    public List<Appointment> listAll() throws SQLException {
        return appointmentDao.findAll();
    }

    private void ensureNoConflict(Appointment appointment) throws SQLException {
        List<Appointment> overlaps = appointmentDao.findByDoctorAndRange(
                appointment.getDoctorUserId(),
                appointment.getStartAt(),
                appointment.getEndAt());
        boolean conflict = overlaps.stream()
                .anyMatch(existing -> !existing.getId().equals(appointment.getId()));
        if (conflict) {
            throw new IllegalStateException("Appointment time conflicts with an existing booking");
        }
    }
}
