package service;

/*
Author: Joshua Jonathan Bird - 230444032
12/07/2026
 */
//package za.ca.cput.commerce.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.service.OrderService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private static final String ORDER_ID = "ORD333";
    private static final String CUSTOMER_ID = "CUS333";

    private static za.ca.cput.commerce.domain.Order buildOrder() {
        Customer customer = new Customer.Builder()
                .setCustomerId(CUSTOMER_ID)
                .build();

        return new za.ca.cput.commerce.domain.Order.Builder()
                .setOrderId(ORDER_ID)
                .setCustomer(customer)
                .build();
    }

    private static final za.ca.cput.commerce.domain.Order order = buildOrder();

    @Test
    @org.junit.jupiter.api.Order(1)
    void create() {
        za.ca.cput.commerce.domain.Order created = orderService.create(order);

        assertNotNull(created);
        assertEquals(ORDER_ID, created.getOrderId());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void read() {
        za.ca.cput.commerce.domain.Order found = orderService.read(ORDER_ID);

        assertNotNull(found);
        assertEquals(ORDER_ID, found.getOrderId());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void update() {
        za.ca.cput.commerce.domain.Order updatedOrder =
                new za.ca.cput.commerce.domain.Order.Builder()
                        .copy(order)
                        .build();

        za.ca.cput.commerce.domain.Order updated =
                orderService.update(updatedOrder);

        assertNotNull(updated);
        assertEquals(ORDER_ID, updated.getOrderId());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void getAll() {
        List<za.ca.cput.commerce.domain.Order> orders = orderService.getAll();

        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void getOrdersByCustomer() {
        List<za.ca.cput.commerce.domain.Order> orders =
                orderService.getOrdersByCustomer(CUSTOMER_ID);

        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void delete() {
        boolean deleted = orderService.delete(ORDER_ID);

        assertTrue(deleted);
        assertNull(orderService.read(ORDER_ID));
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void deleteNonExistingOrder() {
        boolean deleted = orderService.delete("NON_EXISTENT_ORDER");

        assertFalse(deleted);
    }
}