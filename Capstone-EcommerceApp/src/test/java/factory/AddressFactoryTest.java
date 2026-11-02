/* AddressFactoryTest.java
   AddressFactoryTest class
   Author: 222709006 Qhama dyushu
   Date: 21 June 2026
*/
package factory;

import za.ca.cput.commerce.domain.Address;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.factory.AddressFactory;

import static org.junit.jupiter.api.Assertions.*;

class AddressFactoryTest {

    private Customer sampleCustomer() {
        return new Customer.Builder()
                .setCustomerId("CUST-202")
                .setName("John Doe")
                .setEmail("john@example.com")
                .setPhone("0821234567")
                .build();
    }

    @Test
    void testCreateAddressSuccess() {
        Address address = AddressFactory.createAddress(
                sampleCustomer(),          // was: "CUST-202"
                "123 Main St",
                "Cape Town",
                "Western Cape",
                "8001",
                "South Africa",
                "Shipping"
        );

        assertNotNull(address);
        assertEquals("ADDR-101", address.getAddressId());
        assertEquals("CUST-202", address.getCustomer().getCustomerId());   // was: address.getCustomerId()
        assertEquals("123 Main St", address.getStreetAddress());
        assertEquals("Cape Town", address.getCity());
        assertEquals("Western Cape", address.getState());
        assertEquals("8001", address.getPostalCode());
        assertEquals("South Africa", address.getCountry());
        assertEquals("Shipping", address.getAddressType());
    }

    @Test
    void testCreateAddressFail() {
        Address address = AddressFactory.createAddress(
                sampleCustomer(),
                "123 Main St",
                "Cape Town",
                "Western Cape",
                "8001",
                "South Africa",
                "Shipping"
        );
        assertNull(address);
    }
}