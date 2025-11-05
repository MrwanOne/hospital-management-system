package com.acme.hms.service;

import com.acme.hms.dao.AppointmentDao;
import com.acme.hms.model.Appointment;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for appointments including conflict detection and validation.
 */
public class AppointmentService {
    private final AppointmentDao appointmentDao;

    public AppointmentService(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    public List<Appointment> listAppointments() throws SQLException {
        return appointmentDao.findAll();
    }

    public Optional<Appointment> getAppointment(long id) throws SQLException {
        return appointmentDao.findById(id);
    }

    public long schedule(Appointment appointment) throws SQLException {
        validateAppointment(appointment);
        return appointmentDao.create(appointment);
    }

    public void update(Appointment appointment) throws SQLException {
        validateAppointment(appointment);
        appointmentDao.update(appointment);
    }

    public void cancel(long appointmentId) throws SQLException {
        appointmentDao.delete(appointmentId);
    }

    private void validateAppointment(Appointment appointment) throws SQLException {
        if (appointment.getStartAt() == null || appointment.getEndAt() == null) {
            throw new IllegalArgumentException("Appointment must have start and end time");
        }
        if (!appointment.getEndAt().isAfter(appointment.getStartAt())) {
            throw new IllegalArgumentException("Appointment end time must be after start time");
        }
        if (Duration.between(appointment.getStartAt(), appointment.getEndAt()).toMinutes() < 5) {
            throw new IllegalArgumentException("Appointment duration must be at least 5 minutes");
        }
        if (appointmentDao.hasConflict(appointment)) {
            throw new IllegalArgumentException("Appointment conflicts with an existing booking");
        }
    }
}
