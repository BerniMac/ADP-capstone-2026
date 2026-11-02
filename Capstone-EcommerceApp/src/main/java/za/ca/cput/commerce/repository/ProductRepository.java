package za.ca.cput.commerce.repository;

/*
Author: Plamedie 230082629

 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByProductNameIgnoreCase(String productName);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    boolean existsByProductNameIgnoreCase(String productName);
}
