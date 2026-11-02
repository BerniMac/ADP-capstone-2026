package za.ca.cput.commerce.service;

import za.ca.cput.commerce.domain.Address;

import java.util.List;

public interface AddressService {

    Address createAddress(Address address);

    Address getAddressById(String addressId);

    List<Address> getAllAddresses();

    Address updateAddress(String addressId, Address address);

    void deleteAddress(String addressId);

    List<Address> getAddressesByCustomerId(String customerId);
}
