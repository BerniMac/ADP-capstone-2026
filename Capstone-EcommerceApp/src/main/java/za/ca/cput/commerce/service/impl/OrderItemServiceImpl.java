package za.ca.cput.commerce.service.impl;

/*
Author: Joshua Jonathan Bird - 230444032
12/07/2026
 */
import org.springframework.transaction.annotation.Transactional;
import za.ca.cput.commerce.repository.InventoryRepository;
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
    private final InventoryRepository inventoryRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository,
                                OrderRepository orderRepository,
                                ProductRepository productRepository,
                                InventoryRepository inventoryRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
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
    @Transactional
    public OrderItem createWithStock(OrderItem orderItem) {

        if (orderItem == null || orderItem.getQuantity() <= 0 || orderItem.getProduct() == null) {
            return null;
        }

        // The usual checks and save: the order and the product must exist
        OrderItem created = create(orderItem);
        if (created == null) {
            return null;
        }

        // Take the items out of stock. If there is not enough, undo the save.
        int changed = inventoryRepository.reduceStock(
                orderItem.getProduct().getProductId(), orderItem.getQuantity());
        if (changed == 0) {
            orderItemRepository.deleteById(created.getOrderItemId());
            return null;
        }

        return created;
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