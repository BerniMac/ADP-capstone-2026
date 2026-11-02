package za.ca.cput.commerce.repository;

/*
Author: Plamedie 230082629

 */

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Inventory;
import za.ca.cput.commerce.domain.Product;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, String> {

    Optional<Inventory> findByProduct_ProductId(String productId);

    boolean existsByProduct(Product product);

}
