package za.ca.cput.commerce.service;

/*
Author: Joshua Jonathan Bird - 230444032
12/07/2026
 */

import za.ca.cput.commerce.domain.Order;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    Order read(String orderId);

    Order update(Order order);

    boolean delete(String orderId);

    List<Order> getAll();

    List<Order> getOrdersByCustomer(String customerId);
}
