package org.howard.edu.lsp.assignment3.transform;

import org.howard.edu.lsp.assignment3.model.Product;

/**
 * A single transformation step that mutates a {@link Product} in place.
 *
 * <p>Polymorphism point for the pipeline; each concrete implementation performs
 * exactly one well-defined rule from the assignment.</p>
 */
public interface Transformation {
    /**
     * Applies the transformation rule to the given product.
     * @param product product to mutate; never {@code null}.
     */
    void apply(Product product);
}
