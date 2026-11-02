/*
 * ProductFactory.java
 * Product Factory class
 * Author: Plamedie 230082629
 * Date: 24 March 2026
 */
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Product;
public class ProductFactory {

    private ProductFactory() {
    }

    public static Product createProduct(String productName,
                                        String description,
                                        double currentPrice) {

        if (productName == null || productName.isBlank()) {
            return null;
        }

        if (description == null || description.isBlank()) {
            return null;
        }

        if (currentPrice <= 0) {
            return null;
        }

        return new Product.Builder()
                .setProductName(productName)
                .setDescription(description)
                .setCurrentPrice(currentPrice)
                .build();
    }
}
