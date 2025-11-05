package com.acme.hms.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Loads application configuration from an external properties file.
 */
public final class AppConfig {
    private static final String DEFAULT_CONFIG_PATH = "config/app.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        load(DEFAULT_CONFIG_PATH);
    }

    private AppConfig() {
    }

    public static void load(String path) {
        try (InputStream inputStream = Files.newInputStream(Path.of(path))) {
            PROPERTIES.clear();
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load configuration from " + path, e);
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getOrDefault(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }
}
