package com.acme.hms.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PasswordHasherTest {

    @Test
    void hashesAndVerifiesPassword() {
        String hash = PasswordHasher.hash("secret");
        Assertions.assertTrue(PasswordHasher.matches("secret", hash));
    }
}
