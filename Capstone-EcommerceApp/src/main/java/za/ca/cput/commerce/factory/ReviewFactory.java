/* ReviewFactory.java
   ReviewFactory class
   Author: isheanesu chowuraya (223182192)
   Date: 21 June 2026
*/
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.domain.Review;


public class ReviewFactory {

    public static Review createReview(Customer customer,
                                      Product product,
                                      int rating,
                                      String comment) {

        if (customer == null) {
            return null;
        }

        if (product == null) {
            return null;
        }

        if (rating < 1 || rating > 5) {
            return null;
        }

        if (comment == null || comment.trim().isEmpty()) {
            return null;
        }

        return new Review.Builder()
                .setCustomer(customer)
                .setProduct(product)
                .setRating(rating)
                .setComment(comment.trim())
                .build();
    }
}
