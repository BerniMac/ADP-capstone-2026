/* ReviewFactoryTest.java
   ReviewFactoryTest class
   Author: isheanesu chowuraya (223182192)
   Date: 21 June 2026
*/
package factory;

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Product;
import za.ca.cput.commerce.domain.Review;
import org.junit.jupiter.api.Test;
import za.ca.cput.commerce.factory.ReviewFactory;

import static org.junit.jupiter.api.Assertions.*;

class ReviewFactoryTest {

    private Customer sampleCustomer() {
        return new Customer.Builder()
                .setCustomerId("CUST-001")
                .setName("John Doe")
                .setEmail("john@example.com")
                .setPhone("0821234567")
                .build();
    }

    private Product sampleProduct() {
        return new Product.Builder()
                .setProductId("PROD-002")
                .setProductName("Laptop Stand")
                .setDescription("Adjustable aluminum laptop stand")
                .setCurrentPrice(45.00)
                .build();
    }

    @Test
    void testCreateReviewSuccess() {
        Review review = ReviewFactory.createReview(
                sampleCustomer(),          // was: "CUST-001"
                sampleProduct(),           // was: "PROD-002"
                5,
                "Great product, highly recommend!"
        );

        assertNotNull(review);
        assertEquals("REV-77", review.getReviewId());
        assertEquals("CUST-001", review.getCustomer().getCustomerId());  // was: review.getCustomerId()
        assertEquals("PROD-002", review.getProduct().getProductId());   // was: review.getProductId()
        assertEquals(5, review.getRating());
        assertEquals("Great product, highly recommend!", review.getComment());
        assertEquals("2026-06-21", review.getReviewDate());
    }

    @Test
    void testCreateReviewFail() {
        Review review = ReviewFactory.createReview(

                sampleCustomer(),
                sampleProduct(),
                6, // invalid rating (max 5)
                "Great product, highly recommend!"

        );
        assertNull(review);
    }
}

