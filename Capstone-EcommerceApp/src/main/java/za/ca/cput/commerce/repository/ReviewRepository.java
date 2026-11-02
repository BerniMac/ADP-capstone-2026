package za.ca.cput.commerce.repository;

/*
Author: isheanesu chowuraya (223182192)

 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.domain.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findByProduct(Product product);

    List<Review> findByCustomer(Customer customer);

    List<Review> findByProductProductId(String productId);

    List<Review> findByCustomerCustomerId(String customerId);
}