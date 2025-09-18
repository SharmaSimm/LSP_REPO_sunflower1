package org.howard.edu.lsp.assignment3.transform;

import org.howard.edu.lsp.assignment3.model.Product;

import java.math.BigDecimal;

/**
 * Step 3: if final price &gt; threshold AND original category was "Electronics",
 * set the category to "Premium Electronics".
 */
public class RecategorizePremium implements Transformation {
    private final BigDecimal threshold;

    public RecategorizePremium(BigDecimal threshold) {
        this.threshold = threshold;
    }

    @Override
    public void apply(Product product) {
        if ("Electronics".equalsIgnoreCase(product.getOriginalCategory())
                && product.getPrice().compareTo(threshold) > 0) {
            product.setCategory("Premium Electronics");
        }
    }
}
