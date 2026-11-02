package za.ca.cput.commerce.service;

/*
Author: Joshua Jonathan Bird - 230444032
12/07/2026
 */

import za.ca.cput.commerce.domain.OrderItem;

import java.util.List;


public interface OrderItemService {

    OrderItem create(OrderItem orderItem);

    OrderItem read(String orderItemId);

    OrderItem update(String orderItemId, OrderItem orderItem);

    void delete(String orderItemId);

    List<OrderItem> getAll();

    List<OrderItem> getByOrder(String orderId);

    List<OrderItem> getByProduct(String productId);
}