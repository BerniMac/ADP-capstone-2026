package service;

/*
Author: 222709006 Qhama dyushu
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.repository.CustomerRepository;
import za.ca.cput.commerce.service.impl.CustomerServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import za.ca.cput.commerce.domain.Customer;
//import za.ca.cput.commerce.domain.Name;
import za.ca.cput.commerce.service.CustomerService;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.service.CustomerService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    private static Customer buildCustomer() {
        return new Customer.Builder()
                .setCustomerId("222")
                .setName("John Doe")
                .setEmail("john@gmail.com")
                .setPhone("123456789")
                .build();
    }

    private static final Customer customer = buildCustomer();

    @Test
    @Order(1)
    void create() {
        Customer created = customerService.create(customer);

        assertNotNull(created);
        assertEquals(customer.getCustomerId(), created.getCustomerId());
    }

    @Test
    @Order(2)
    void read() {
        Customer read = customerService.read(customer.getCustomerId());

        assertNotNull(read);
        assertEquals(customer.getCustomerId(), read.getCustomerId());
    }

    @Test
    @Order(3)
    void update() {
        Customer customerUpdated = new Customer.Builder()
                .copy(customer)
                .setName("John Smith")
                .setEmail("updated@gmail.com")
                .setPhone("987654321")
                .build();

        Customer updated = customerService.update(
                customer.getCustomerId(), customerUpdated);

        assertNotNull(updated);
        assertEquals(customer.getCustomerId(), updated.getCustomerId());
        assertEquals("updated@gmail.com", updated.getEmail());
        assertEquals("987654321", updated.getPhone());
    }

    @Test
    @Order(4)
    void getAll() {
        List<Customer> customers = customerService.getAll();

        assertNotNull(customers);
        assertFalse(customers.isEmpty());
    }

    @Test
    @Order(5)
    void findByEmail() {
        Optional<Customer> found =
                customerService.findByEmail("updated@gmail.com");

        assertTrue(found.isPresent());
        assertEquals(customer.getCustomerId(),
                found.get().getCustomerId());
    }

    @Test
    @Order(6)
    void searchByName() {
        List<Customer> customers =
                customerService.searchByName("John");

        assertNotNull(customers);
        assertFalse(customers.isEmpty());
    }

    @Test
    @Order(7)
    void delete() {
        boolean deleted =
                customerService.delete(customer.getCustomerId());

        assertTrue(deleted);
        assertNull(customerService.read(customer.getCustomerId()));
    }
}