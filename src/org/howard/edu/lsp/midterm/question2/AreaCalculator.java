package org.howard.edu.lsp.midterm.question2;

/**
 * Utility for computing areas of basic shapes.
 */
public class AreaCalculator {

    // Circle area
    public static double area(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be > 0");
        }
        return Math.PI * radius * radius;
    }

    // Rectangle area
    public static double area(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be > 0");
        }
        return width * height;
    }

    // Triangle (base & height) area
    public static double area(int base, int height) {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("base and height must be > 0");
        }
        return 0.5 * base * height;
    }

    // Square (side length) area
    public static double area(int side) {
        if (side <= 0) {
            throw new IllegalArgumentException("side must be > 0");
        }
        return (double) side * side;
    }
}
