package com.acme.hms.dao;

import com.acme.hms.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {

    private static final String BASE_SELECT = "SELECT id, username, pass_hash, role, active, created_at FROM users";

    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = BASE_SELECT + " WHERE username = ?";
        return findOne(sql, ps -> ps.setString(1, username), this::mapRow);
    }

    public List<User> findAll() throws SQLException {
        return findMany(BASE_SELECT, ps -> { }, this::mapRow);
    }

    public long insert(User user) throws SQLException {
        String sql = "INSERT INTO users (username, pass_hash, role, active) VALUES (?,?,?,?)";
        return insert(sql, ps -> {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.setBoolean(4, user.isActive());
        });
    }

    public int update(User user) throws SQLException {
        String sql = "UPDATE users SET pass_hash=?, role=?, active=? WHERE id=?";
        return update(sql, ps -> {
            ps.setString(1, user.getPasswordHash());
            ps.setString(2, user.getRole());
            ps.setBoolean(3, user.isActive());
            ps.setLong(4, user.getId());
        });
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("pass_hash"));
        user.setRole(rs.getString("role"));
        user.setActive(rs.getBoolean("active"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        user.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : LocalDateTime.now());
        return user;
    }
}
