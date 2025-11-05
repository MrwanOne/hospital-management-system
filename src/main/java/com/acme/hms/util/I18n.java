package com.acme.hms.util;

import com.acme.hms.config.AppConfig;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class I18n {

    private static ResourceBundle bundle;

    static {
        reload();
    }

    private I18n() {
    }

    public static void reload() {
        String localeCode = AppConfig.getOrDefault("app.locale", "en");
        Locale locale = switch (localeCode) {
            case "ar" -> new Locale.Builder().setLanguage("ar").setRegion("SA").build();
            default -> Locale.ENGLISH;
        };
        bundle = ResourceBundle.getBundle("i18n.messages", locale);
    }

    public static String tr(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException ex) {
            return '!' + key + '!';
        }
    }
}
