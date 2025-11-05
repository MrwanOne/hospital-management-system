package com.acme.hms.dao;

import com.acme.hms.config.Database;
import com.acme.hms.model.Appointment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * DAO for {@link Appointment} entities.
 */
public class AppointmentDao extends BaseDao<Appointment> {
    private static final String SELECT_ALL = "SELECT * FROM appointments ORDER BY start_at DESC";
    private static final String SELECT_BY_ID = "SELECT * FROM appointments WHERE id = ?";
    private static final String SELECT_CONFLICT = "SELECT COUNT(*) AS cnt FROM appointments WHERE doctor_user_id = ?"
            + " AND ((? BETWEEN start_at AND end_at) OR (? BETWEEN start_at AND end_at)"
            + " OR (start_at BETWEEN ? AND ?) OR (end_at BETWEEN ? AND ?)) AND status <> 'CANCELLED'"
            + " AND id <> COALESCE(?, -1)";
    private static final String INSERT = "INSERT INTO appointments (patient_id, doctor_user_id, start_at, end_at, status, notes)"
            + " VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE appointments SET patient_id = ?, doctor_user_id = ?, start_at = ?, end_at = ?, status = ?, notes = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM appointments WHERE id = ?";

    public List<Appointment> findAll() throws SQLException {
        return queryForList(SELECT_ALL, statement -> { });
    }

    public Optional<Appointment> findById(long id) throws SQLException {
        return queryForObject(SELECT_BY_ID, statement -> statement.setLong(1, id));
    }

    public boolean hasConflict(Appointment appointment) throws SQLException {
        try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_CONFLICT)) {
            statement.setLong(1, appointment.getDoctorUserId());
            statement.setObject(2, appointment.getStartAt());
            statement.setObject(3, appointment.getEndAt());
            statement.setObject(4, appointment.getStartAt());
            statement.setObject(5, appointment.getEndAt());
            statement.setObject(6, appointment.getStartAt());
            statement.setObject(7, appointment.getEndAt());
            if (appointment.getId() == null) {
                statement.setNull(8, java.sql.Types.BIGINT);
            } else {
                statement.setLong(8, appointment.getId());
            }
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cnt") > 0;
                }
                return false;
            }
        }
    }

    public long create(Appointment appointment) throws SQLException {
        return executeInsert(INSERT, statement -> {
            statement.setLong(1, appointment.getPatientId());
            statement.setLong(2, appointment.getDoctorUserId());
            statement.setObject(3, appointment.getStartAt());
            statement.setObject(4, appointment.getEndAt());
            statement.setString(5, appointment.getStatus());
            statement.setString(6, appointment.getNotes());
        });
    }

    public int update(Appointment appointment) throws SQLException {
        return executeUpdate(UPDATE, statement -> {
            statement.setLong(1, appointment.getPatientId());
            statement.setLong(2, appointment.getDoctorUserId());
            statement.setObject(3, appointment.getStartAt());
            statement.setObject(4, appointment.getEndAt());
            statement.setString(5, appointment.getStatus());
            statement.setString(6, appointment.getNotes());
            statement.setLong(7, appointment.getId());
        });
    }

    public int delete(long id) throws SQLException {
        return executeUpdate(DELETE, statement -> statement.setLong(1, id));
    }

    @Override
    protected Appointment mapRow(ResultSet resultSet) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(resultSet.getLong("id"));
        appointment.setPatientId(resultSet.getLong("patient_id"));
        appointment.setDoctorUserId(resultSet.getLong("doctor_user_id"));
        appointment.setStartAt(resultSet.getObject("start_at", LocalDateTime.class));
        appointment.setEndAt(resultSet.getObject("end_at", LocalDateTime.class));
        appointment.setStatus(resultSet.getString("status"));
        appointment.setNotes(resultSet.getString("notes"));
        return appointment;
    }
}
