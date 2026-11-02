package za.ca.cput.commerce.factory;
/*
cardFactory.java
Author:isheanesu chowurya 223182192
date:26March 2026
 */
import za.ca.cput.commerce.domain.Card;

public class CardFactory {

    public static Card createCard(String cardHolderName,
                                  String cardType,
                                  String cardNumber,
                                  String cardExpiry,
                                  String cardCVV) {
if(cardHolderName == null || cardType == null || cardNumber == null){
    return null;
}
        return new Card.Builder()
                .setCardHolderName(cardHolderName)
                .setCardType(cardType)
                .setCardNumber(cardNumber)
                .setCardExpiry(cardExpiry)
                .setCardCVV(cardCVV)
                .build();
    }
}
