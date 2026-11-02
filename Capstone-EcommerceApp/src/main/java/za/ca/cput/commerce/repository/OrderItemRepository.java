package za.ca.cput.commerce.repository;

/*
Author: Joshua Jonathan Bird - 230444032

 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.OrderItem;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

    List<OrderItem> findByOrderOrderId(String orderId);

    List<OrderItem> findByProductProductId(String productId);

}