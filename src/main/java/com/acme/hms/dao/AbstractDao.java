package com.acme.hms.dao;

import com.acme.hms.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Generic DAO helper methods for CRUD operations.
 *
 * @param <T> entity type
 */
public abstract class AbstractDao<T> {

    protected interface PreparedStatementConfigurer {
        void configure(PreparedStatement statement) throws SQLException;
    }

    protected Optional<T> findOne(String sql, PreparedStatementConfigurer configurer, Function<ResultSet, T> mapper)
            throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            configurer.configure(statement);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(mapper.apply(rs));
                }
                return Optional.empty();
            }
        }
    }

    protected List<T> findMany(String sql, PreparedStatementConfigurer configurer, Function<ResultSet, T> mapper)
            throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            configurer.configure(statement);
            try (ResultSet rs = statement.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(mapper.apply(rs));
                }
                return results;
            }
        }
    }

    protected long insert(String sql, PreparedStatementConfigurer configurer) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            configurer.configure(statement);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            return -1;
        }
    }

    protected int update(String sql, PreparedStatementConfigurer configurer) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            configurer.configure(statement);
            return statement.executeUpdate();
        }
    }
}
