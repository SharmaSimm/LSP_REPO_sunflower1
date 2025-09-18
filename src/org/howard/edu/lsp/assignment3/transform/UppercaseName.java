package org.howard.edu.lsp.assignment3.transform;

import org.howard.edu.lsp.assignment3.model.Product;

/** Step 1: convert the product name to UPPERCASE. */
public class UppercaseName implements Transformation {
    @Override
    public void apply(Product product) {
        product.setName(product.getName().toUpperCase());
    }
}
