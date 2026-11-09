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
import org.springframework.transaction.annotation.Transactional;
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.repository.InventoryRepository;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final InventoryRepository inventoryRepository;

    public OrderServiceImpl(OrderRepository repository, InventoryRepository inventoryRepository) {
        this.repository = repository;
        this.inventoryRepository = inventoryRepository;
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
    @Transactional
    public boolean delete(String orderId) {

        Order order = repository.findById(orderId).orElse(null);
        if (order == null) {
            return false;
        }

        // Put the items back in stock, unless the order was cancelled (that already did it)
        boolean cancelled = order.getShipment() != null
                && "CANCELLED".equalsIgnoreCase(order.getShipment().getStatus());
        if (!cancelled) {
            for (OrderItem item : order.getOrderItems()) {
                inventoryRepository.addStock(item.getProduct().getProductId(), item.getQuantity());
            }
        }

        repository.deleteById(orderId);
        return true;
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