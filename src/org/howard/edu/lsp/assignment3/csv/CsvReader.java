package org.howard.edu.lsp.assignment3.csv;

import org.howard.edu.lsp.assignment3.model.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Extract step: reads {@code data/products.csv} (relative path) and parses each row into a {@link Product}.
 *
 * <p>Assumptions from the spec:</p>
 * <ul>
 *   <li>Comma delimiter; no embedded quotes or commas in fields.</li>
 *   <li>First line is a header; it is read and ignored for transformation.</li>
 * </ul>
 *
 * <p>Error-handling:</p>
 * <ul>
 *   <li>If the file is missing, a {@link MissingInputException} is thrown.</li>
 *   <li>Malformed rows are returned as {@code null} and can be counted as skipped by the caller.</li>
 * </ul>
 */
public class CsvReader {
    private final Path inputPath;
    private int rowsRead = 0; // data rows read (excludes header)

    public CsvReader(Path inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * Extracts all products from the CSV file.
     *
     * @return list of {@link Product}; may be empty if file has only a header or is empty.
     * @throws MissingInputException if {@code inputPath} does not exist.
     * @throws IOException           on I/O errors.
     */
    public List<Product> extract() throws MissingInputException, IOException {
        if (!Files.exists(inputPath)) {
            throw new MissingInputException();
        }

        List<Product> result = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String header = reader.readLine(); // may be null for truly empty file
            if (header == null) {
                return result;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // ignore blank lines
                }
                Product p = parse(line);
                if (p != null) {
                    result.add(p);
                    rowsRead++;
                }
            }
        }
        return result;
    }

    /** @return number of data rows read (excludes header). */
    public int getRowsRead() {
        return rowsRead;
    }

    /** Parses one CSV line into a {@link Product}, or returns {@code null} if malformed. */
    private Product parse(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 4) return null;

        try {
            int productId = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            BigDecimal price = new BigDecimal(parts[2].trim());
            String category = parts[3].trim();
            return new Product(productId, name, price, category);
        } catch (Exception ex) {
            return null;
        }
    }

    /** Signals that the input CSV is missing (required by the assignment). */
    public static class MissingInputException extends Exception {
        private static final long serialVersionUID = 1L;
    }
}
