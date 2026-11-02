package za.ca.cput.commerce.service;

/*
Author: Plamedie 230082629
12/07/2026
 */

import za.ca.cput.commerce.domain.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);

    Product getById(String productId);

    List<Product> getAll();

    Product update(String productId, Product product);

    void delete(String productId);

    List<Product> searchByName(String name);
}
