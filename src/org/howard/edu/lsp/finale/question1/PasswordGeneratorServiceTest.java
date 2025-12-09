package org.howard.edu.lsp.finale.question1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorServiceTest {

    private PasswordGeneratorService service;

    @BeforeEach
    public void setup() {
        service = PasswordGeneratorService.getInstance();
    }

    @Test
    public void checkInstanceNotNull() {
        assertNotNull(service);
    }

    @Test
    public void checkSingleInstanceBehavior() {
        PasswordGeneratorService second = PasswordGeneratorService.getInstance();
        assertSame(service, second);
    }

    @Test
    public void generateWithoutSettingAlgorithmThrowsException() {
        assertThrows(IllegalStateException.class, () -> {
            service.generatePassword(5);
        });
    }

    @Test
    public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
        service.setAlgorithm("basic");
        String password = service.generatePassword(10);
        assertEquals(10, password.length());
        assertTrue(password.matches("[0-9]+"));
    }

    @Test
    public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
        service.setAlgorithm("enhanced");
        String password = service.generatePassword(12);
        assertEquals(12, password.length());
        assertTrue(password.matches("[A-Za-z0-9]+"));
    }

    @Test
    public void lettersAlgorithmGeneratesLettersOnly() {
        service.setAlgorithm("letters");
        String password = service.generatePassword(8);
        assertEquals(8, password.length());
        assertTrue(password.matches("[A-Za-z]+"));
    }

    @Test
    public void switchingAlgorithmsChangesBehavior() {
        // BASIC: digits only
        service.setAlgorithm("basic");
        String p1 = service.generatePassword(10);
        assertEquals(10, p1.length());
        assertTrue(p1.matches("[0-9]+"));

        // LETTERS: letters only
        service.setAlgorithm("letters");
        String p2 = service.generatePassword(10);
        assertEquals(10, p2.length());
        assertTrue(p2.matches("[A-Za-z]+"));

        // ENHANCED: letters OR digits
        service.setAlgorithm("enhanced");
        String p3 = service.generatePassword(10);
        assertEquals(10, p3.length());
        assertTrue(p3.matches("[A-Za-z0-9]+"));
    }

}