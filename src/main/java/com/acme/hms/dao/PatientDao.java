package com.acme.hms.dao;

import com.acme.hms.model.Patient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class PatientDao extends AbstractDao<Patient> {

    private static final String BASE_SELECT = "SELECT id, mrn, full_name, dob, gender, phone, address, created_at FROM patients";

    public List<Patient> findAll() throws SQLException {
        return findMany(BASE_SELECT, ps -> { }, this::mapRow);
    }

    public Optional<Patient> findByMrn(String mrn) throws SQLException {
        return findOne(BASE_SELECT + " WHERE mrn = ?", ps -> ps.setString(1, mrn), this::mapRow);
    }

    public long insert(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (mrn, full_name, dob, gender, phone, address) VALUES (?,?,?,?,?,?)";
        return insert(sql, ps -> {
            ps.setString(1, patient.getMrn());
            ps.setString(2, patient.getFullName());
            if (patient.getDob() != null) {
                ps.setDate(3, java.sql.Date.valueOf(patient.getDob()));
            } else {
                ps.setDate(3, null);
            }
            ps.setString(4, patient.getGender());
            ps.setString(5, patient.getPhone());
            ps.setString(6, patient.getAddress());
        });
    }

    public int update(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET full_name=?, dob=?, gender=?, phone=?, address=? WHERE id=?";
        return update(sql, ps -> {
            ps.setString(1, patient.getFullName());
            if (patient.getDob() != null) {
                ps.setDate(2, java.sql.Date.valueOf(patient.getDob()));
            } else {
                ps.setDate(2, null);
            }
            ps.setString(3, patient.getGender());
            ps.setString(4, patient.getPhone());
            ps.setString(5, patient.getAddress());
            ps.setLong(6, patient.getId());
        });
    }

    private Patient mapRow(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getLong("id"));
        patient.setMrn(rs.getString("mrn"));
        patient.setFullName(rs.getString("full_name"));
        java.sql.Date dob = rs.getDate("dob");
        patient.setDob(dob != null ? dob.toLocalDate() : null);
        patient.setGender(rs.getString("gender"));
        patient.setPhone(rs.getString("phone"));
        patient.setAddress(rs.getString("address"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        patient.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);
        return patient;
    }
}
