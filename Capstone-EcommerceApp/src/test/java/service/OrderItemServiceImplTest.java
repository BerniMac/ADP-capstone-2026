package service;

/*
Author: Joshua Jonathan Bird - 230444032
1/07/2026
*/
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.service.OrderItemService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderItemServiceImplTest {

    @Autowired
    private OrderItemService orderItemService;

    private static OrderItem buildOrderItem() {
        za.ca.cput.commerce.domain.Order order =
                new za.ca.cput.commerce.domain.Order.Builder()
                        .setOrderId("ORD222")
                        .build();

        Product product = new Product.Builder()
                .setProductId("P222")
                .build();

        return new OrderItem.Builder()
                .setOrder(order)
                .setProduct(product)
                .setQuantity(2)
                .setPriceAtPurchase(100.00)
                .build();
    }

    private static final OrderItem orderItem = buildOrderItem();

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        OrderItem created = orderItemService.create(orderItem);

        assertNotNull(created);
        assertNotNull(created.getOrderItemId());
        assertEquals(orderItem.getQuantity(), created.getQuantity());
        assertEquals(orderItem.getPriceAtPurchase(),
                created.getPriceAtPurchase());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void read() {
        OrderItem read = orderItemService.read(
                orderItem.getOrderItemId());

        assertNotNull(read);
        assertEquals(orderItem.getOrderItemId(),
                read.getOrderItemId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        OrderItem orderItemUpdated = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(3)
                .setPriceAtPurchase(150.00)
                .build();

        OrderItem updated = orderItemService.update(
                orderItem.getOrderItemId(), orderItemUpdated);

        assertNotNull(updated);
        assertEquals(3, updated.getQuantity());
        assertEquals(150.00, updated.getPriceAtPurchase());
        assertEquals(450.00, updated.getSubtotal());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAll() {
        List<OrderItem> orderItems = orderItemService.getAll();

        assertNotNull(orderItems);
        assertFalse(orderItems.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getByOrder() {
        List<OrderItem> orderItems = orderItemService.getByOrder(
                orderItem.getOrder().getOrderId());

        assertNotNull(orderItems);
        assertFalse(orderItems.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void getByProduct() {
        List<OrderItem> orderItems = orderItemService.getByProduct(
                orderItem.getProduct().getProductId());

        assertNotNull(orderItems);
        assertFalse(orderItems.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void delete() {
        orderItemService.delete(orderItem.getOrderItemId());

        OrderItem deleted = orderItemService.read(
                orderItem.getOrderItemId());

        assertNull(deleted);
    }
}