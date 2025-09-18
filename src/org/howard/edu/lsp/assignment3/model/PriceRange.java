package org.howard.edu.lsp.assignment3.model;

import java.math.BigDecimal;

/**
 * Price buckets computed from the final (post-discount) price.
 *
 * <pre>
 * 0.00–10.00   → Low
 * 10.01–100.00 → Medium
 * 100.01–500.00→ High
 * 500.01+      → Premium
 * </pre>
 */
public enum PriceRange {
    Low, Medium, High, Premium;

    /** Maps a BigDecimal price to the appropriate {@link PriceRange} bucket. */
    public static PriceRange from(BigDecimal price) {
        BigDecimal ten = new BigDecimal("10.00");
        BigDecimal hundred = new BigDecimal("100.00");
        BigDecimal fiveHundred = new BigDecimal("500.00");

        if (price.compareTo(ten) <= 0) return Low;
        if (price.compareTo(ten) > 0 && price.compareTo(hundred) <= 0) return Medium;
        if (price.compareTo(hundred) > 0 && price.compareTo(fiveHundred) <= 0) return High;
        return Premium;
    }
}
