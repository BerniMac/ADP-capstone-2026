package za.ca.cput.commerce.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Address;
import za.ca.cput.commerce.repository.AddressRepository;
import za.ca.cput.commerce.service.AddressService;

import java.util.List;


@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address getAddressById(String addressId) {
        return addressRepository.findById(addressId).orElse(null);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address updateAddress(String addressId, Address address) {

        Address existingAddress = addressRepository.findById(addressId).orElse(null);

        if (existingAddress == null) {
            return null;
        }

        Address updatedAddress = new Address.Builder()
                .copy(existingAddress)
                .setCustomer(address.getCustomer())
                .setStreetAddress(address.getStreetAddress())
                .setCity(address.getCity())
                .setState(address.getState())
                .setPostalCode(address.getPostalCode())
                .setCountry(address.getCountry())
                .setAddressType(address.getAddressType())
                .build();

        return addressRepository.save(updatedAddress);
    }

    @Override
    public void deleteAddress(String addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public List<Address> getAddressesByCustomerId(String customerId) {
        return addressRepository.findByCustomerCustomerId(customerId);
    }
}
