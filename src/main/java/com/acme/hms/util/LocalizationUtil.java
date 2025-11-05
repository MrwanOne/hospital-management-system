package com.acme.hms.util;

import com.acme.hms.config.AppConfig;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility around {@link ResourceBundle} providing i18n lookups.
 */
public final class LocalizationUtil {
    private static final String BUNDLE_NAME = "i18n/messages";
    private static ResourceBundle bundle = loadBundle(AppConfig.getOrDefault("app.locale", "en"));

    private LocalizationUtil() {
    }

    public static void setLocale(String localeCode) {
        bundle = loadBundle(localeCode);
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException exception) {
            return '!' + key + '!';
        }
    }

    private static ResourceBundle loadBundle(String localeCode) {
        Locale locale = Locale.forLanguageTag(localeCode.replace('_', '-'));
        return ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }
}
