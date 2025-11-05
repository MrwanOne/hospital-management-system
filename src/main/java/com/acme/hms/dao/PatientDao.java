package com.acme.hms.dao;

import com.acme.hms.model.Patient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * DAO for {@link Patient} entities.
 */
public class PatientDao extends BaseDao<Patient> {
    private static final String SELECT_ALL = "SELECT * FROM patients ORDER BY created_at DESC";
    private static final String SELECT_BY_ID = "SELECT * FROM patients WHERE id = ?";
    private static final String INSERT = "INSERT INTO patients (mrn, full_name, dob, gender, phone, address, created_at)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE patients SET mrn = ?, full_name = ?, dob = ?, gender = ?, phone = ?, address = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM patients WHERE id = ?";

    public List<Patient> findAll() throws SQLException {
        return queryForList(SELECT_ALL, PreparedStatement::clearParameters);
    }

    public Optional<Patient> findById(long id) throws SQLException {
        return queryForObject(SELECT_BY_ID, statement -> statement.setLong(1, id));
    }

    public long create(Patient patient) throws SQLException {
        return executeInsert(INSERT, statement -> {
            statement.setString(1, patient.getMrn());
            statement.setString(2, patient.getFullName());
            statement.setObject(3, patient.getDateOfBirth());
            statement.setString(4, patient.getGender());
            statement.setString(5, patient.getPhone());
            statement.setString(6, patient.getAddress());
            statement.setObject(7, patient.getCreatedAt());
        });
    }

    public int update(Patient patient) throws SQLException {
        return executeUpdate(UPDATE, statement -> {
            statement.setString(1, patient.getMrn());
            statement.setString(2, patient.getFullName());
            statement.setObject(3, patient.getDateOfBirth());
            statement.setString(4, patient.getGender());
            statement.setString(5, patient.getPhone());
            statement.setString(6, patient.getAddress());
            statement.setLong(7, patient.getId());
        });
    }

    public int delete(long id) throws SQLException {
        return executeUpdate(DELETE, statement -> statement.setLong(1, id));
    }

    @Override
    protected Patient mapRow(ResultSet resultSet) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setMrn(resultSet.getString("mrn"));
        patient.setFullName(resultSet.getString("full_name"));
        patient.setDateOfBirth(resultSet.getObject("dob", LocalDate.class));
        patient.setGender(resultSet.getString("gender"));
        patient.setPhone(resultSet.getString("phone"));
        patient.setAddress(resultSet.getString("address"));
        patient.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
        return patient;
    }
}
