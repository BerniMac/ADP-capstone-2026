package service;

/*
2026/07/12
Author: Tlangelani Chauke
 */

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ca.cput.commerce.domain.Shipment;
import za.ca.cput.commerce.service.ShipmentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShipmentServiceImplTest {

    @Autowired
    private ShipmentService shipmentService;

    private static Shipment buildShipment() {
        return new Shipment.Builder()
                .setShipmentId("222")
                .build();
    }

    private static final Shipment shipment = buildShipment();

    @Test
    @Order(1)
    void create() {
        Shipment created = shipmentService.create(shipment);

        assertNotNull(created);
        assertEquals(
                shipment.getShipmentId(),
                created.getShipmentId()
        );
    }

    @Test
    @Order(2)
    void read() {
        Shipment read =
                shipmentService.read(shipment.getShipmentId());

        assertNotNull(read);
        assertEquals(
                shipment.getShipmentId(),
                read.getShipmentId()
        );
    }

    @Test
    @Order(3)
    void update() {
        Shipment shipmentUpdated = new Shipment.Builder()
                .copy(shipment)
                .setShipmentId(shipment.getShipmentId())
                .build();

        Shipment updated = shipmentService.update(shipmentUpdated);

        assertNotNull(updated);
        assertEquals(
                shipmentUpdated.getShipmentId(),
                updated.getShipmentId()
        );
    }

    @Test
    @Order(4)
    void getAll() {
        List<Shipment> shipments = shipmentService.getAll();

        assertNotNull(shipments);
        assertFalse(shipments.isEmpty());
    }

    @Test
    @Order(5)
    void findByOrderId() {
        Shipment found =
                shipmentService.findByOrderId("111");

        // The order must have an associated shipment.
        // Replace "111" with a valid order ID in your test database.
        assertNotNull(found);
    }

    @Test
    @Order(6)
    void delete() {
        shipmentService.delete(shipment.getShipmentId());

        Shipment deleted =
                shipmentService.read(shipment.getShipmentId());

        assertNull(deleted);
    }
}
