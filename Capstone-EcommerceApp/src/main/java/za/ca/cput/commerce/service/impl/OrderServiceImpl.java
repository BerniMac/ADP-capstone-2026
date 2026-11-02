package za.ca.cput.commerce.service.impl;

/*
Author: Joshua Jonathan Bird - 230444032
12/07/2026
 */

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.repository.OrderRepository;
import za.ca.cput.commerce.service.OrderService;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    public Order read(String orderId) {
        return repository.findById(orderId).orElse(null);
    }

    @Override
    public Order update(Order order) {

        if (repository.existsById(order.getOrderId())) {
            return repository.save(order);
        }

        return null;
    }

    @Override
    public boolean delete(String orderId) {

        if (repository.existsById(orderId)) {
            repository.deleteById(orderId);
            return true;
        }

        return false;
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomer(String customerId) {
        return repository.findByCustomerCustomerId(customerId);
    }
}