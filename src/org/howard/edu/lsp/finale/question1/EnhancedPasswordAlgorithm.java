package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * Enhanced password algorithm that uses upper/lowercase letters and digits.
 */
public class EnhancedPasswordAlgorithm implements PasswordAlgorithm {
    private static final String ALLOWED =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789";

    private final SecureRandom random = new SecureRandom();

    @Override
    public String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException(
                    "Password length must be greater than zero.");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {     // IMPORTANT: i < length
            int index = random.nextInt(ALLOWED.length());
            sb.append(ALLOWED.charAt(index));  // actually add each char
        }
        return sb.toString();
    }
}
