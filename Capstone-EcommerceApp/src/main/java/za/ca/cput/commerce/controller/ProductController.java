package za.ca.cput.commerce.controller;

/*
Author: Plamedie 230082629
19/07/2026
 */
import org.springframework.http.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.service.ProductService;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {

        Product createdProduct = productService.create(product);

        if (createdProduct == null) {
            return ResponseEntity.badRequest().body(Map.of("message",
                    "Product not created. Check the name, description and price, "
                            + "and that the name is not already used."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(
            @PathVariable String productId) {

        Product product = productService.getById(productId);

        return ResponseEntity.ok(product);

    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {

        return ResponseEntity.ok(productService.getAll());

    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam String productName) {

        return ResponseEntity.ok(
                productService.searchByName(productName));

    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String productId,
            @RequestBody Product product) {

        if (productService.getById(productId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "That product no longer exists."));
        }

        if (product.getProductName() == null || product.getProductName().isBlank()
                || product.getDescription() == null || product.getDescription().isBlank()
                || product.getCurrentPrice() <= 0) {
            return ResponseEntity.badRequest().body(Map.of("message",
                    "Please give the product a name, a description and a price above zero."));
        }

        return ResponseEntity.ok(productService.update(productId, product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {

        if (productService.getById(productId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "That product no longer exists."));
        }

        try {
            productService.delete(productId);
        } catch (DataIntegrityViolationException e) {
            // The database refuses because orders or reviews still point at this product
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message",
                    "This product is part of an order or has reviews, so it cannot be deleted."));
        }

        return ResponseEntity.noContent().build();
    }

}