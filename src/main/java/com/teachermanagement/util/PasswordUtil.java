package com.teachermanagement.util;

import java.util.Base64;

public class PasswordUtil {
    // Hash/encrypt password or authentication code using Base64
    public static String encrypt(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    // Verify/decrypt password or authentication code
    public static boolean verify(String input, String encrypted) {
        String decoded = new String(Base64.getDecoder().decode(encrypted));
        return input.equals(decoded);
    }
}