package org.howard.edu.lsp.assignment3.csv;

import org.howard.edu.lsp.assignment3.model.Product;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

/**
 * Load step: writes transformed products to {@code data/transformed_products.csv}
 * with the exact required header and column order.
 *
 * <p>Writes to a temp file first and atomically replaces the target to avoid
 * partial writes and to cope with transient locks (e.g., when the file is
 * open in Excel).</p>
 */
public class CsvWriter {
    private static final String HEADER = "ProductID,Name,Price,Category,PriceRange";
    private final Path outputPath;

    public CsvWriter(Path outputPath) {
        this.outputPath = outputPath;
    }

    /** Ensures the output folder exists and writes a file containing only the header line. */
    public void writeHeaderOnly() throws IOException {
        ensureParentDir();
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write(HEADER);
            writer.newLine();
        }
    }

    /**
     * Writes the full output CSV.
     *
     * @param products transformed products to serialize.
     * @throws IOException on I/O errors.
     */
    public void load(List<Product> products) throws IOException {
        ensureParentDir();

        Path tempOut = outputPath.getParent().resolve(".tmp_transformed_products.csv");
        try (BufferedWriter writer = Files.newBufferedWriter(tempOut, StandardCharsets.UTF_8)) {
            writer.write(HEADER);
            writer.newLine();
            for (Product p : products) {
                writer.write(p.toCsv());
                writer.newLine();
            }
        }
        Files.move(tempOut, outputPath, StandardCopyOption.REPLACE_EXISTING);
    }

    private void ensureParentDir() throws IOException {
        Files.createDirectories(outputPath.getParent());
    }
}
