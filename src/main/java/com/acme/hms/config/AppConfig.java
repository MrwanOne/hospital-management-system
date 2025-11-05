package com.acme.hms.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Loads application configuration from the external config/app.properties file.
 */
public final class AppConfig {

    private static final String CONFIG_PATH = "config/app.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        reload();
    }

    private AppConfig() {
    }

    public static void reload() {
        Path path = Paths.get(CONFIG_PATH);
        try (InputStream is = Files.newInputStream(path)) {
            PROPERTIES.clear();
            PROPERTIES.load(is);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to load configuration from " + path.toAbsolutePath(), ex);
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getOrDefault(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }
}
