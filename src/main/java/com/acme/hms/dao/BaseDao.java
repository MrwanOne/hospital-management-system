package com.acme.hms.dao;

import com.acme.hms.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Simplifies CRUD operations by providing helper methods to execute JDBC statements.
 *
 * @param <T> domain type
 */
public abstract class BaseDao<T> {

    protected abstract T mapRow(ResultSet resultSet) throws SQLException;

    protected Optional<T> queryForObject(String sql, SqlConsumer<PreparedStatement> binder)
            throws SQLException {
        try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            binder.accept(statement);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(mapRow(rs));
                }
                return Optional.empty();
            }
        }
    }

    protected List<T> queryForList(String sql, SqlConsumer<PreparedStatement> binder)
            throws SQLException {
        try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            binder.accept(statement);
            try (ResultSet rs = statement.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
                return results;
            }
        }
    }

    protected long executeInsert(String sql, SqlConsumer<PreparedStatement> binder)
            throws SQLException {
        try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            binder.accept(statement);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
                return -1L;
            }
        }
    }

    protected int executeUpdate(String sql, SqlConsumer<PreparedStatement> binder)
            throws SQLException {
        try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            binder.accept(statement);
            return statement.executeUpdate();
        }
    }

    @FunctionalInterface
    protected interface SqlConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
