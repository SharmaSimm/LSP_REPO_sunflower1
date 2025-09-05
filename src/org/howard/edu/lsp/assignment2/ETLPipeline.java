/**
 * Course: Large Scale Programming
 * Name: Shikshya Sharma
 * Assignment: CSV ETL Pipeline (Assignment 2 for CSCI 363)
 * Package: org.howard.edu.lsp.assignment2
 *
 * Description:
 *  - Reads data/products.csv (relative path)
 *  - Transforms rows per spec:
 *      (1) Uppercase Name
 *      (2) 10% discount for original "Electronics", round HALF_UP to 2 decimals
 *      (3) If final price > 500.00 AND original category == "Electronics" -> Category = "Premium Electronics"
 *      (4) Add PriceRange from final price
 *  - Writes data/transformed_products.csv with header:
 *      ProductID,Name,Price,Category,PriceRange
 *
 * Error Handling:
 *  - Missing input: print clear error and exit without writing output.
 *  - Empty input (header only): write output with header only.
 *  - Malformed rows: skip, count as "skipped".
 *
 * Run Summary (printed at end):
 *  - rows read, transformed, skipped; output path written
 *
 * AI Usage:
 *  - Added a brief AI usage summary in README.
 */

package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ETLPipeline {
    // Relative paths from the project root (working directory)
    private static final Path INPUT_PATH = Paths.get("data", "products.csv");
    private static final Path OUTPUT_PATH = Paths.get("data", "transformed_products.csv");

    // Output header (exact order required)
    private static final String OUTPUT_HEADER = "ProductID,Name,Price,Category,PriceRange";

    public static void main(String[] args) {
        int rowsRead = 0;        // number of data rows read (excludes header)
        int transformed = 0;     // number of rows successfully transformed
        int skipped = 0;         // malformed rows skipped

        // 1) Extract: open input (handle missing file)
        if (!Files.exists(INPUT_PATH)) {
            System.err.println("ERROR: Missing input file: " + INPUT_PATH.toString());
            System.err.println("Place products.csv under the 'data/' folder next to 'src/'.");
            return; // exit gracefully
        }

        // Ensure data/ directory exists for writing output
        try {
            Files.createDirectories(OUTPUT_PATH.getParent());
        } catch (IOException e) {
            System.err.println("ERROR: Unable to ensure output directory exists: " + e.getMessage());
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(INPUT_PATH, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(OUTPUT_PATH, StandardCharsets.UTF_8)) {

            // Read header from input (handle empty files)
            String header = reader.readLine();
            // Always write output header
            writer.write(OUTPUT_HEADER);
            writer.newLine();

            if (header == null) {
                // Truly empty file (no header). Treat as empty dataset; summary only.
                printSummary(rowsRead, transformed, skipped, OUTPUT_PATH);
                return;
            }

            // Process each data row
            String line;
            while ((line = reader.readLine()) != null) {
                rowsRead++;
                String transformedLine = transformLine(line);
                if (transformedLine == null) {
                    skipped++;
                    continue;
                }
                writer.write(transformedLine);
                writer.newLine();
                transformed++;
            }

        } catch (IOException e) {
            System.err.println("ERROR: I/O while processing files: " + e.getMessage());
            return;
        }

        // 3) Print run summary
        printSummary(rowsRead, transformed, skipped, OUTPUT_PATH);
    }

    /**
     * Transforms a single CSV data line according to the assignment rules.
     * Expected input columns: ProductID,Name,Price,Category
     * Returns a CSV line with columns: ProductID,Name,Price,Category,PriceRange
     * or null if the row is malformed and should be skipped.
     */
    private static String transformLine(String line) {
        // Basic CSV split (spec guarantees no commas/quotes inside fields)
        String[] parts = line.split(",", -1);
        if (parts.length != 4) {
            return null; // malformed row
        }

        String productIdStr = parts[0].trim();
        String name = parts[1].trim();
        String priceStr = parts[2].trim();
        String category = parts[3].trim();

        // Validate and parse ProductID and Price
        int productId;
        BigDecimal price;
        try {
            productId = Integer.parseInt(productIdStr);
            price = new BigDecimal(priceStr);
        } catch (NumberFormatException ex) {
            return null; // malformed numeric values
        }

        // Transform order:
        // (1) Uppercase name
        String nameUpper = name.toUpperCase();

        // Track original category for rule (3)
        String originalCategory = category;

        // (2) Discount if original category is "Electronics"
        BigDecimal finalPrice = price;
        if ("Electronics".equalsIgnoreCase(originalCategory)) {
            finalPrice = price.multiply(new BigDecimal("0.90"));
        }

        // Round HALF_UP to two decimals after any discount
        finalPrice = finalPrice.setScale(2, RoundingMode.HALF_UP);

        // (3) Recategorize if final price > 500.00 AND original category == "Electronics"
        String finalCategory = category;
        if ("Electronics".equalsIgnoreCase(originalCategory)
                && finalPrice.compareTo(new BigDecimal("500.00")) > 0) {
            finalCategory = "Premium Electronics";
        } else {
            // Keep category as-is (but normalize to exact casing for Electronics if desired)
            // The spec only mandates the Premium Electronics change; retain original text otherwise.
        }

        // (4) PriceRange based on FINAL price
        String priceRange = computePriceRange(finalPrice);

        // Output: ProductID,Name,Price,Category,PriceRange
        return productId + "," + nameUpper + "," + finalPrice + "," + finalCategory + "," + priceRange;
    }

    /**
     * Computes PriceRange from final price:
     * 0.00–10.00 → Low
     * 10.01–100.00 → Medium
     * 100.01–500.00 → High
     * 500.01+ → Premium
     */
    private static String computePriceRange(BigDecimal finalPrice) {
        // Use compareTo for precise boundaries
        BigDecimal ten = new BigDecimal("10.00");
        BigDecimal hundred = new BigDecimal("100.00");
        BigDecimal fiveHundred = new BigDecimal("500.00");

        if (finalPrice.compareTo(ten) <= 0) {
            return "Low";
        } else if (finalPrice.compareTo(ten) > 0 && finalPrice.compareTo(hundred) <= 0) {
            return "Medium";
        } else if (finalPrice.compareTo(hundred) > 0 && finalPrice.compareTo(fiveHundred) <= 0) {
            return "High";
        } else {
            return "Premium";
        }
    }

    private static void printSummary(int rowsRead, int transformed, int skipped, Path output) {
        System.out.println("Run Summary");
        System.out.println("-----------");
        System.out.println("Rows read:        " + rowsRead);
        System.out.println("Rows transformed: " + transformed);
        System.out.println("Rows skipped:     " + skipped);
        System.out.println("Output written:   " + output.toString());
    }
}
