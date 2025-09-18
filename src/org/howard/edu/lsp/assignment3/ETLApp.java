package org.howard.edu.lsp.assignment3;

import org.howard.edu.lsp.assignment3.csv.CsvReader;
import org.howard.edu.lsp.assignment3.csv.CsvWriter;
import org.howard.edu.lsp.assignment3.model.Product;
import org.howard.edu.lsp.assignment3.transform.*;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for Assignment 3 (OO redesign of the ETL pipeline).
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Orchestrates Extract → Transform → Load using helper classes.</li>
 *   <li>Builds the ordered transformation pipeline (uppercase → discount → recategorize → price-range).</li>
 *   <li>Maintains and prints a run summary (rows read / transformed / skipped).</li>
 * </ul>
 *
 * <p>Behavior and outputs are identical to Assignment 2.</p>
 */
public class ETLApp {
    private static final Path INPUT_PATH  = Paths.get("data", "products.csv");
    private static final Path OUTPUT_PATH = Paths.get("data", "transformed_products.csv");

    public static void main(String[] args) {
        int rowsRead = 0, transformed = 0, skipped = 0;

        CsvReader reader = new CsvReader(INPUT_PATH);
        CsvWriter writer = new CsvWriter(OUTPUT_PATH);

        // Build the transformation pipeline (ORDER MATTERS)
        List<Transformation> pipeline = new ArrayList<>();
        pipeline.add(new UppercaseName());
        pipeline.add(new DiscountElectronics(new BigDecimal("0.10")));     // 10% off for original Electronics
        pipeline.add(new RecategorizePremium(new BigDecimal("500.00")));   // > $500 → Premium Electronics
        pipeline.add(new ComputePriceRange());                              // Low/Medium/High/Premium

        try {
            List<Product> products = reader.extract(); // may be empty
            rowsRead = reader.getRowsRead();

            // Empty input (header only): still create an output file with header
            if (products.isEmpty() && rowsRead == 0) {
                writer.writeHeaderOnly();
                printSummary(rowsRead, transformed, skipped);
                return;
            }

            List<Product> out = new ArrayList<>();
            for (Product p : products) {
                if (p == null) {
                    skipped++;
                    continue;
                }
                for (Transformation step : pipeline) {
                    step.apply(p);
                }
                out.add(p);
                transformed++;
            }

            writer.load(out);
        } catch (CsvReader.MissingInputException e) {
            System.err.println("ERROR: Missing input file: " + INPUT_PATH);
            System.err.println("Place products.csv under the 'data/' folder next to 'src/'.");
            return;
        } catch (Exception e) {
            System.err.println("ERROR: I/O while processing files: " + e.getMessage());
            return;
        }

        printSummary(rowsRead, transformed, skipped);
    }

    /** Prints the standard run summary required by the assignment. */
    private static void printSummary(int rowsRead, int transformed, int skipped) {
        System.out.println("Run Summary");
        System.out.println("-----------");
        System.out.println("Rows read:        " + rowsRead);
        System.out.println("Rows transformed: " + transformed);
        System.out.println("Rows skipped:     " + skipped);
        System.out.println("Output written:   data\\transformed_products.csv");
    }
}
