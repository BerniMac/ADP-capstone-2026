package za.ca.cput.commerce.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {

    List<Address> findByCustomerCustomerId(String customerId);

}
