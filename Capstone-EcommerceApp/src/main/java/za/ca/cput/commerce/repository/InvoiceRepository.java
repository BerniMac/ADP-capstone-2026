package za.ca.cput.commerce.repository;

/*
Author: Mogamad Jawaad Allie - 230472125

 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Invoice;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

    Optional<Invoice> findByOrderOrderId(String orderId);

}
