package org.howard.edu.lsp.assignment3.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Domain model representing one product row.
 *
 * <p>Encapsulates both original and current (transformed) state. Rounding policy
 * (two decimals, HALF_UP) is enforced in {@link #setPrice(BigDecimal)} to keep
 * consistency across transformations.</p>
 */
public class Product {
    private final int productId;
    private String name;
    private BigDecimal price;              // current price (may be discounted)
    private String category;               // current category (may be recategorized)
    private final String originalCategory; // needed for A2/A3 rules
    private String priceRange;             // derived by transformation

    public Product(int productId, String name, BigDecimal price, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
        this.category = category;
        this.originalCategory = category;
    }

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public String getOriginalCategory() { return originalCategory; }
    public String getPriceRange() { return priceRange; }

    public void setName(String name) { this.name = name; }

    /** Sets price and enforces two decimals with HALF_UP rounding. */
    public void setPrice(BigDecimal newPrice) {
        this.price = newPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public void setCategory(String category) { this.category = category; }
    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }

    /** Serializes the product using the required output column order. */
    public String toCsv() {
        String range = (priceRange == null) ? "" : priceRange;
        return productId + "," + name + "," + price + "," + category + "," + range;
    }
}
