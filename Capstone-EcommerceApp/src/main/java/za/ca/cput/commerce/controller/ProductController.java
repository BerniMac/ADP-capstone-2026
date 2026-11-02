package za.ca.cput.commerce.controller;

/*
Author: Plamedie 230082629
19/07/2026
 */
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.service.ProductService;


import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        Product createdProduct = productService.create(product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdProduct);

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
    public ResponseEntity<Product> updateProduct(
            @PathVariable String productId,
            @RequestBody Product product) {

        Product updatedProduct =
                productService.update(productId, product);

        return ResponseEntity.ok(updatedProduct);

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String productId) {

        productService.delete(productId);

        return ResponseEntity.noContent().build();

    }

}