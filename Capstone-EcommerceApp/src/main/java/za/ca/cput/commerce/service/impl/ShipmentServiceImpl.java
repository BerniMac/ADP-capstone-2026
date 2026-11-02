package za.ca.cput.commerce.service.impl;

/*
Author: Tlangelani Chauke
12/07/2026
 */

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Shipment;
import za.ca.cput.commerce.repository.ShipmentRepository;
import za.ca.cput.commerce.service.ShipmentService;

import java.util.List;
@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository repository;

    public ShipmentServiceImpl(ShipmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Shipment create(Shipment shipment) {
        return repository.save(shipment);
    }

    @Override
    public Shipment read(String shipmentId) {
        return repository.findById(shipmentId).orElse(null);
    }

    @Override
    public Shipment update(Shipment shipment) {

        if (!repository.existsById(shipment.getShipmentId())) {
            return null;
        }

        return repository.save(shipment);
    }

    @Override
    public void delete(String shipmentId) {
        repository.deleteById(shipmentId);
    }

    @Override
    public List<Shipment> getAll() {
        return repository.findAll();
    }

    @Override
    public Shipment findByOrderId(String orderId) {
        return repository.findByOrderOrderId(orderId).orElse(null);
    }
}