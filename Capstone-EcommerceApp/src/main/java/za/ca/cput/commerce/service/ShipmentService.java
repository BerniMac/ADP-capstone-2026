package za.ca.cput.commerce.service;

/*
Author: Tlangelani Chauke
12/07/2026
 */
import za.ca.cput.commerce.domain.Shipment;

import java.util.List;

public interface ShipmentService {

    Shipment create(Shipment shipment);

    Shipment read(String shipmentId);

    Shipment update(Shipment shipment);

    void delete(String shipmentId);

    List<Shipment> getAll();

    Shipment findByOrderId(String orderId);

}
