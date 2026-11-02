package za.ca.cput.commerce.repository;

/*
Author: Tlangelani Chauke

 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Shipment;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {

    Optional<Shipment> findByOrderOrderId(String orderId);

}
