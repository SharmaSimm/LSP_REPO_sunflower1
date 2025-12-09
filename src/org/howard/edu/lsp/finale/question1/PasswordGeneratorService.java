package org.howard.edu.lsp.finale.question1;

/**
 * Central service for generating passwords using different algorithms.
 * Implements the Singleton pattern and delegates generation to a selected
 * PasswordAlgorithm strategy.
 */
public class PasswordGeneratorService {
    // Singleton instance
    private static final PasswordGeneratorService INSTANCE =
            new PasswordGeneratorService();

    // Currently selected strategy
    private PasswordAlgorithm algorithm;

    /** Private constructor to prevent external instantiation. */
    private PasswordGeneratorService() { }

    /**
     * Returns the single shared instance of the service.
     *
     * @return singleton instance
     */
    public static PasswordGeneratorService getInstance() {
        return INSTANCE;
    }

    /**
     * Selects which password-generation algorithm will be used.
     *
     * @param name algorithm name ("basic", "enhanced", "letters")
     * @throws IllegalArgumentException if the name is null or unknown
     */
    public void setAlgorithm(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Algorithm name cannot be null");
        }

        switch (name.toLowerCase()) {
            case "basic":
                this.algorithm = new BasicPasswordAlgorithm();
                break;
            case "enhanced":
                this.algorithm = new EnhancedPasswordAlgorithm();
                break;
            case "letters":
                this.algorithm = new LettersPasswordAlgorithm();
                break;
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + name);
        }
    }

    /**
     * Generates a password using the currently selected algorithm.
     *
     * @param length desired password length, must be positive
     * @return generated password
     * @throws IllegalStateException if no algorithm has been selected
     * @throws IllegalArgumentException if length is not positive
     */
    public String generatePassword(int length) {
        if (algorithm == null) {
            throw new IllegalStateException("No password algorithm has been selected.");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be greater than zero.");
        }
        return algorithm.generate(length);
    }

    /*
     * =======================
     * DESIGN PATTERNS USED
     * =======================
     * Singleton:
     *  - PasswordGeneratorService has a private constructor and exposes
     *    a single shared instance via getInstance().
     *
     * Strategy:
     *  - PasswordAlgorithm defines the algorithm interface.
     *  - BasicPasswordAlgorithm, EnhancedPasswordAlgorithm, and
     *    LettersPasswordAlgorithm are concrete strategies.
     *  - The service delegates to the selected strategy in generatePassword().
     *
     * These patterns provide a single access point for password generation
     * and allow the algorithm behavior to be selected and changed at runtime.
     */
}
