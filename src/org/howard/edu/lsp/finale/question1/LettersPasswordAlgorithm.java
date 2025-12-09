package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * Password algorithm that generates passwords using only letters A–Z and a–z.
 */
public class LettersPasswordAlgorithm implements PasswordAlgorithm {
    private static final String LETTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";

    private final SecureRandom random = new SecureRandom();

    @Override
    public String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException(
                    "Password length must be greater than zero.");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {     // IMPORTANT: i < length
            int index = random.nextInt(LETTERS.length());
            sb.append(LETTERS.charAt(index));  // append each char
        }
        return sb.toString();
    }
}
