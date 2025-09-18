package org.howard.edu.lsp.assignment3.transform;

import org.howard.edu.lsp.assignment3.model.Product;

import java.math.BigDecimal;

/**
 * Step 2: apply a percentage discount to products whose <em>original</em> category is "Electronics".
 * The {@link Product} enforces HALF_UP rounding to two decimals.
 */
public class DiscountElectronics implements Transformation {
    private final BigDecimal rate; // e.g., 0.10 for 10%

    public DiscountElectronics(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public void apply(Product product) {
        if ("Electronics".equalsIgnoreCase(product.getOriginalCategory())) {
            BigDecimal discounted = product.getPrice().multiply(BigDecimal.ONE.subtract(rate));
            product.setPrice(discounted);
        }
    }
}
