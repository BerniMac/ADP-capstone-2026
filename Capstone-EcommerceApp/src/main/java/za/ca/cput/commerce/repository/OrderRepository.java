package za.ca.cput.commerce.repository;

/*
Author: Joshua Jonathan Bird - 230444032

 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByCustomer(Customer customer);

    List<Order> findByCustomerCustomerId(String customerId);
}
