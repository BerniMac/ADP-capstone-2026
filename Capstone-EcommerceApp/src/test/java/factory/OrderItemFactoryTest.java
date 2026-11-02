/* OrderItemFactoryTest.java
   OrderItemFactoryTest class
   Author: Joshua Jonathan Bird - 230444032
   Date: 21 June 2026
*/
package factory;

import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.OrderItem;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.factory.OrderItemFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class OrderItemFactoryTest {

    private Order sampleOrder() {
        return new Order.Builder()
                .setOrderId("ORD-999")
                .setOrderDate(LocalDateTime.now())
                .setTotalAmount(300.00)
                .build();
    }

    private Product sampleProduct() {
        return new Product.Builder()
                .setProductId("PROD-007")
                .setProductName("Bluetooth Speaker")
                .setDescription("Portable speaker")
                .setCurrentPrice(150.00)
                .build();
    }

    @Test
    void testCreateOrderItemSuccess() {
        OrderItem item = OrderItemFactory.createOrderItem(

                sampleOrder(),             // was: "ORD-999"
                sampleProduct(),           // was: "PROD-007"
                2,
                150.00
        );

        assertNotNull(item);
        assertEquals("ITEM-001", item.getOrderItemId());
        assertEquals("ORD-999", item.getOrder().getOrderId());       // was: item.getOrderId()
        assertEquals("PROD-007", item.getProduct().getProductId());  // was: item.getProductId()
        assertEquals(2, item.getQuantity());
        assertEquals(150.00, item.getPriceAtPurchase());
    }

    @Test
    void testCreateOrderItemFail() {
        OrderItem item = OrderItemFactory.createOrderItem(

                sampleOrder(),
                sampleProduct(),
                0, // invalid quantity
                150.00
        );
        assertNull(item);
    }
}


