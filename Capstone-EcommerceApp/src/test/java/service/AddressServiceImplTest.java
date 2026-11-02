package service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Address;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.factory.AddressFactory;
import za.ca.cput.commerce.repository.AddressRepository;
import za.ca.cput.commerce.service.impl.AddressServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import za.ca.cput.commerce.domain.Address;
import za.ca.cput.commerce.service.AddressService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressServiceImplTest {

    @Autowired
    private AddressService addressService;

    private static Address buildAddress() {
        return new Address.Builder()
                .setStreetAddress("123 Main Street")
                .setCity("Cape Town")
                .setState("Western Cape")
                .setPostalCode("8001")
                .setCountry("South Africa")
                .setAddressType("Home")
                .build();
    }

    private static final Address address = buildAddress();

    @Test
    @Order(1)
    void createAddress() {
        Address created = addressService.createAddress(address);

        assertNotNull(created);
        assertNotNull(created.getAddressId());
        assertEquals(address.getStreetAddress(), created.getStreetAddress());
    }

    @Test
    @Order(2)
    void getAddressById() {
        Address read = addressService.getAddressById(address.getAddressId());

        assertNotNull(read);
        assertEquals(address.getAddressId(), read.getAddressId());
    }

    @Test
    @Order(3)
    void updateAddress() {
        Address addressUpdated = new Address.Builder()
                .copy(address)
                .setStreetAddress("456 Main Street")
                .setCity("Johannesburg")
                .setState("Gauteng")
                .build();

        Address updated = addressService.updateAddress(
                address.getAddressId(), addressUpdated);

        assertNotNull(updated);
        assertEquals("456 Main Street", updated.getStreetAddress());
        assertEquals("Johannesburg", updated.getCity());
    }

    @Test
    @Order(4)
    void getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();

        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
    }

    @Test
    @Order(5)
    void getAddressesByCustomerId() {
        List<Address> addresses =
                addressService.getAddressesByCustomerId(
                        address.getCustomer().getCustomerId());

        assertNotNull(addresses);
    }

    @Test
    @Order(6)
    void deleteAddress() {
        addressService.deleteAddress(address.getAddressId());

        Address deleted =
                addressService.getAddressById(address.getAddressId());

        assertNull(deleted);
    }
}