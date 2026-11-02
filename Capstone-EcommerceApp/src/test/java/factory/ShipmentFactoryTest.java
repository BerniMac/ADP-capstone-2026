/*
 * ShipmentFactoryTest.java
 * Author: Tlangelani Chauke
 * Date:22march 2026
 */
package factory;

import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.Shipment;
import za.ca.cput.commerce.factory.ShipmentFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Date;

public class ShipmentFactoryTest {

    @Test
    void createShipment() {
        Shipment shipment = ShipmentFactory.createShipment(
                "Cape Town",
                LocalDate.now(),
                LocalDate.now(),
                "Shipped",
                new Order.Builder()
                        .setOrderId("ORD001")
                        .build()
        );

        assertNotNull(shipment);
        assertNotNull(shipment.getShipmentId());
    }
}
