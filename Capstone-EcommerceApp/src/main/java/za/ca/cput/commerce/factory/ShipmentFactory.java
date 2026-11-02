/*
 * ShipmentFactory.java
 * Author: Tlangelani Chauke
 * Date:22march 2026
 */
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.Shipment;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ShipmentFactory {

    private ShipmentFactory() {
    }

    public static Shipment createShipment(
            String address,
            LocalDate shipmentDate,
            LocalDate deliveryDate,
            String status,
            Order order) {

      if(order == null){
          return null;
      }
        return new Shipment.Builder()
                .setAddress(address)
                .setShipmentDate(LocalDate.now().atStartOfDay())
                .setDeliveryDate(LocalDate.now().atStartOfDay())
                .setStatus(status)
                .setOrder(order)
                .build();
    }
}