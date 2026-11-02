/* AddressFactory.java
   AddressFactory class
   Author: 222709006 Qhama dyushu
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Address;
import za.ca.cput.commerce.domain.Customer;

public class AddressFactory {

    private AddressFactory() {
    }

    public static Address createAddress(Customer customer,
                                        String streetAddress,
                                        String city,
                                        String state,
                                        String postalCode,
                                        String country,
                                        String addressType) {

        return new Address.Builder()
                .setCustomer(customer)
                .setStreetAddress(streetAddress)
                .setCity(city)
                .setState(state)
                .setPostalCode(postalCode)
                .setCountry(country)
                .setAddressType(addressType)
                .build();
    }
}
