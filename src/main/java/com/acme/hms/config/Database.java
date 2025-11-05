package com.acme.hms.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides pooled database connections backed by HikariCP.
 */
public final class Database {

    private static final Logger LOG = LoggerFactory.getLogger(Database.class);
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = Duration.ofSeconds(2).toMillis();

    private static HikariDataSource dataSource;

    private Database() {
    }

    public static synchronized void init() {
        if (Objects.nonNull(dataSource)) {
            return;
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(AppConfig.get("db.url"));
        config.setUsername(AppConfig.get("db.user"));
        config.setPassword(AppConfig.get("db.password"));
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(TimeUnit.SECONDS.toMillis(30));
        config.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(60));
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        init();
        SQLException last = null;
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                return dataSource.getConnection();
            } catch (SQLException ex) {
                last = ex;
                LOG.warn("Database connection attempt {} failed: {}", attempt, ex.getMessage());
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new SQLException("Interrupted while waiting to retry connection", ie);
                }
            }
        }
        throw last != null ? last : new SQLException("Unable to obtain connection");
    }

    public static synchronized void shutdown() {
        if (Objects.nonNull(dataSource)) {
            dataSource.close();
            dataSource = null;
        }
    }
}
