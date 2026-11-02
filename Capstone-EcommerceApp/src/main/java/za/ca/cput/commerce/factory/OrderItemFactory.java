/* OrderItemFactory.java
   OrderItemFactory class
   Author: Joshua Jonathan Bird - 230444032
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Order;
import za.ca.cput.commerce.domain.OrderItem;
import za.ca.cput.commerce.domain.Product;

public class OrderItemFactory {

    public static OrderItem createOrderItem(Order order,
                                            Product product,
                                            int quantity,
                                            double priceAtPurchase) {

        if (order == null) {
            return null;
        }

        if (product == null) {
            return null;
        }

        if (quantity <= 0) {
            return null;
        }

        if (priceAtPurchase < 0) {
            return null;
        }

        return new OrderItem.Builder()
                .setOrder(order)
                .setProduct(product)
                .setQuantity(quantity)
                .setPriceAtPurchase(priceAtPurchase)
                .build();
    }
}