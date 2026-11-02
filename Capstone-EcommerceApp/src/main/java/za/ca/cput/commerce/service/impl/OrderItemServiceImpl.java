package za.ca.cput.commerce.service.impl;

/*
Author: Joshua Jonathan Bird - 230444032
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.repository.OrderItemRepository;
import za.ca.cput.commerce.repository.OrderRepository;
import za.ca.cput.commerce.repository.ProductRepository;
import za.ca.cput.commerce.service.OrderItemService;

import java.util.List;
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository,
                                OrderRepository orderRepository,
                                ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderItem create(OrderItem orderItem) {

        if (orderItem == null) {
            return null;
        }

        Order order = orderRepository.findById(orderItem.getOrder().getOrderId())
                .orElse(null);

        if (order == null) {
            return null;
        }

        Product product = productRepository.findById(orderItem.getProduct().getProductId())
                .orElse(null);

        if (product == null) {
            return null;
        }

        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem read(String orderItemId) {
        return orderItemRepository.findById(orderItemId).orElse(null);
    }

    @Override
    public OrderItem update(String orderItemId, OrderItem orderItem) {

        OrderItem existing = read(orderItemId);

        if (existing == null) {
            return null;
        }

        Order order = orderRepository.findById(orderItem.getOrder().getOrderId())
                .orElse(null);

        if (order == null) {
            return null;
        }

        Product product = productRepository.findById(orderItem.getProduct().getProductId())
                .orElse(null);

        if (product == null) {
            return null;
        }

        OrderItem updated = new OrderItem.Builder()
                .copy(existing)
                .setOrder(orderItem.getOrder())
                .setProduct(orderItem.getProduct())
                .setQuantity(orderItem.getQuantity())
                .setPriceAtPurchase(orderItem.getPriceAtPurchase())
                .build();

        return orderItemRepository.save(updated);
    }

    @Override
    public void delete(String orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }

    @Override
    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public List<OrderItem> getByOrder(String orderId) {
        return orderItemRepository.findByOrderOrderId(orderId);
    }

    @Override
    public List<OrderItem> getByProduct(String productId) {
        return orderItemRepository.findByProductProductId(productId);
    }
}