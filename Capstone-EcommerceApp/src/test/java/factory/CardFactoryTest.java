package factory;
/*
cardFactoryTest.java
Author:isheanesu chowuraya 223182192
date:26 March 2026
 */

import za.ca.cput.commerce.domain.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardFactoryTest {
    private Card card;

    @BeforeEach
    void setUp() {
        card = new Card.Builder()
                .setCardId("id434")
                .setCardNumber("99099452")
                .setCardType("standard")
                .setCardHolderName("john")
                .setCardExpiry("24/04/2028")
                .setCardCVV("9099343")
                .build();
    }
    @Test
    void testId(){
        assertEquals("id434",card.getCardId());
    }
    @Test
    void testCardExpiry(){ assertEquals("24/04/2028",card.getCardExpiry());}
    @Test
    void testCardCVV(){  assertEquals("9099343",card.getCardCVV());}
    @Test
    void testCardType(){ assertEquals("standard",card.getCardType()); }
    @Test
    void testCardHolderName(){  assertEquals("john",card.getCardHolderName()); }
    @Test
    void testCardNumber(){   assertEquals("99099452",card.getCardNumber()); }

    @AfterEach
    void tearDown(){
        card = null;
    }
}
