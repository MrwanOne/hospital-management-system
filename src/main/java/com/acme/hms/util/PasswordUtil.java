package com.acme.hms.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Provides BCrypt hashing helpers for user passwords.
 */
public final class PasswordUtil {
    private PasswordUtil() {
    }

    public static String hashPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt(12));
    }

    public static boolean matches(String plainText, String hash) {
        return BCrypt.checkpw(plainText, hash);
    }
}
