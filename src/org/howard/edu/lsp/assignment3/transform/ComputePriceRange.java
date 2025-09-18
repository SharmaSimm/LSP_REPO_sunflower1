package org.howard.edu.lsp.assignment3.transform;

import org.howard.edu.lsp.assignment3.model.PriceRange;
import org.howard.edu.lsp.assignment3.model.Product;

/** Step 4: compute the PriceRange from the final (post-discount) price. */
public class ComputePriceRange implements Transformation {
    @Override
    public void apply(Product product) {
        product.setPriceRange(PriceRange.from(product.getPrice()).name());
    }
}
