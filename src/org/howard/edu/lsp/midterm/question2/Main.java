package org.howard.edu.lsp.midterm.question2;

/**
 * Demonstrates AreaCalculator and exception handling per exam specification.
 */
public class Main {
    public static void main(String[] args) {
        // Circle radius 3.0 → area = 28.274333882308138
        System.out.println("Circle radius 3.0 → area = " + AreaCalculator.area(3.0));

        // Rectangle 5.0 x 2.0 → area = 10.0
        System.out.println("Rectangle 5.0 x 2.0 → area = " + AreaCalculator.area(5.0, 2.0));

        // Triangle base 10, height 6 → area = 30.0
        System.out.println("Triangle base 10, height 6 → area = " + AreaCalculator.area(10, 6));

        // Square side 4 → area = 16.0
        System.out.println("Square side 4 → area = " + AreaCalculator.area(4));

        // Cause an IllegalArgumentException and catch it
        try {
            // calling with zero side to produce exception
            AreaCalculator.area(0);
            System.out.println("ERROR: Exception was not thrown for invalid dimension!");
        } catch (IllegalArgumentException e) {
            // print an error message to System.out
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }

    /*
     * Overloading rationale (2-3 sentences):
     * Overloading keeps a single conceptual operation ("area") as one method name while allowing different argument
     * types for different shapes; it improves discoverability and groups related functionality. Alternatively, named
     * methods (circleArea, rectangleArea, ...) are clearer in intent but scatter the "area" concept across names — both
     * approaches can be valid; overloading suits the exam requirement and yields a compact API.
     */
}
