package org.howard.edu.lsp.finale.question1;

public interface PasswordAlgorithm {
    /**
     * Generates a password of the given length according to the algorithm rules.
     *
     * @param length desired password length
     * @return generated password
     */
    String generate(int length);
}
