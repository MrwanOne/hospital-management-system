package com.acme.hms.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordHasher {

    private static final int WORKLOAD = 10;

    private PasswordHasher() {
    }

    public static String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(WORKLOAD));
    }

    public static boolean matches(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }
}
