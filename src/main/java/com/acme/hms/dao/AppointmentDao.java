package com.acme.hms.dao;

import com.acme.hms.model.Appointment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDao extends AbstractDao<Appointment> {

    private static final String BASE_SELECT = "SELECT id, patient_id, doctor_user_id, start_at, end_at, status, notes FROM appointments";

    public List<Appointment> findAll() throws SQLException {
        return findMany(BASE_SELECT, ps -> { }, this::mapRow);
    }

    public List<Appointment> findByDoctorAndRange(long doctorId, LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = BASE_SELECT + " WHERE doctor_user_id = ? AND start_at < ? AND end_at > ?";
        return findMany(sql, ps -> {
            ps.setLong(1, doctorId);
            ps.setTimestamp(2, Timestamp.valueOf(end));
            ps.setTimestamp(3, Timestamp.valueOf(start));
        }, this::mapRow);
    }

    public long insert(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, doctor_user_id, start_at, end_at, status, notes) VALUES (?,?,?,?,?,?)";
        return insert(sql, ps -> {
            ps.setLong(1, appointment.getPatientId());
            ps.setLong(2, appointment.getDoctorUserId());
            ps.setTimestamp(3, Timestamp.valueOf(appointment.getStartAt()));
            ps.setTimestamp(4, Timestamp.valueOf(appointment.getEndAt()));
            ps.setString(5, appointment.getStatus());
            ps.setString(6, appointment.getNotes());
        });
    }

    private Appointment mapRow(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getLong("id"));
        appointment.setPatientId(rs.getLong("patient_id"));
        appointment.setDoctorUserId(rs.getLong("doctor_user_id"));
        appointment.setStartAt(rs.getTimestamp("start_at").toLocalDateTime());
        appointment.setEndAt(rs.getTimestamp("end_at").toLocalDateTime());
        appointment.setStatus(rs.getString("status"));
        appointment.setNotes(rs.getString("notes"));
        return appointment;
    }
}
