package com.acme.hms.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides pooled database connections backed by HikariCP.
 */
public final class Database {
    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
    private static final int MAX_RETRIES = 3;
    private static final DataSource DATA_SOURCE = createDataSource();

    private Database() {
    }

    private static DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(AppConfig.get("db.url"));
        config.setUsername(AppConfig.get("db.user"));
        config.setPassword(AppConfig.get("db.password"));
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(10_000);
        config.setLeakDetectionThreshold(30_000);
        return new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        SQLException lastException = null;
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                return DATA_SOURCE.getConnection();
            } catch (SQLException exception) {
                lastException = exception;
                LOGGER.warn("Failed to obtain connection (attempt {}/{}).", attempt, MAX_RETRIES,
                        exception);
                try {
                    Thread.sleep(500L * attempt);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    throw new SQLException("Interrupted while retrying connection", interrupted);
                }
            }
        }
        throw lastException != null ? lastException : new SQLException("Unable to get connection");
    }

    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }
}
