package za.ca.cput.commerce.service.impl;

/*
Author: Plamedie 230082629
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.repository.ProductRepository;
import za.ca.cput.commerce.service.ProductService;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {

        if (product == null) {
            return(null);
        }

        if (product.getProductName() == null || product.getProductName().isBlank()) {
            return(null);
        }

        if (product.getDescription() == null || product.getDescription().isBlank()) {
            return(null);
        }

        if (product.getCurrentPrice() <= 0) {
            return(null);
        }

        if (productRepository.existsByProductNameIgnoreCase(product.getProductName())) {
            return(null);
        }

        return productRepository.save(product);

    }

    @Override
    public Product getById(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public List<Product> getAll() {

        return productRepository.findAll();

    }

    @Override
    public Product update(String productId, Product product) {

        Product existing = getById(productId);

        Product updatedProduct = new Product.Builder()
                .copy(existing)
                .setProductName(product.getProductName())
                .setDescription(product.getDescription())
                .setCurrentPrice(product.getCurrentPrice())
                .build();

        return productRepository.save(updatedProduct);

    }

    @Override
    public void delete(String productId) {

        Product product = getById(productId);

        productRepository.delete(product);

    }

    @Override
    public List<Product> searchByName(String productName) {

        return productRepository
                .findByProductNameContainingIgnoreCase(productName);

    }

}