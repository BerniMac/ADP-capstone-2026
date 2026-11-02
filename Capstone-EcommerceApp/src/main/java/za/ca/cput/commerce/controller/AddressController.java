package za.ca.cput.commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ca.cput.commerce.domain.Address;
import za.ca.cput.commerce.domain.Customer;

import za.ca.cput.commerce.service.AddressService;
import za.ca.cput.commerce.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address createdAddress = addressService.createAddress(address);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable String addressId) {

        Address address = addressService.getAddressById(addressId);

        if (address == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(address);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable String addressId,
                                                 @RequestBody Address address) {

        Address updatedAddress = addressService.updateAddress(addressId, address);

        if (updatedAddress == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String addressId) {

        Address address = addressService.getAddressById(addressId);

        if (address == null) {
            return ResponseEntity.notFound().build();
        }

        addressService.deleteAddress(addressId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Address>> getAddressesByCustomerId(@PathVariable String customerId) {

        List<Address> addresses = addressService.getAddressesByCustomerId(customerId);

        return ResponseEntity.ok(addresses);
    }
}


