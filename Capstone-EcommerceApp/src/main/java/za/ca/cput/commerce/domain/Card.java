package za.ca.cput.commerce.domain;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*
card.java
Autor:isheanesu chowuraya(223182192)
 //date :26 March 2026
 */
@JsonDeserialize(builder = Card.Builder.class)
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cardId;
    private String cardHolderName;
    private String cardType;
    private String cardNumber;
    private String cardExpiry;
    private String cardCVV;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("card-payment")
    private List<Payment> payments = new ArrayList<>();

    protected Card(){}

    private Card(Builder builder){
        this.cardId = builder.cardId;
        this.cardHolderName = builder.cardHolderName;
        this.cardType = builder.cardType;
        this.cardNumber = builder.cardNumber;
        this.cardExpiry = builder.cardExpiry;
        this.cardCVV = builder.cardCVV;
    }

    public String getCardId() {
        return cardId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public List<Payment> getPayments() {
        return payments;
    }
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId='" + cardId + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardExpiry='" + cardExpiry + '\'' +
                ", cardCVV='" + cardCVV + '\'' +
                '}';
    }
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String cardId;
        private String cardHolderName;
        private String cardType;
        private String cardNumber;
        private String cardExpiry;
        private String cardCVV;

        public Builder setCardId(String cardId) {
            this.cardId = cardId;
            return this;
        }
        public Builder setCardHolderName(String cardHolderName) {
            this.cardHolderName = cardHolderName;
            return this;
        }
        public Builder setCardType(String cardType) {
            this.cardType = cardType;
            return this;
        }
        public Builder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }
        public Builder setCardExpiry(String cardExpiry) {
            this.cardExpiry = cardExpiry;
            return this;
        }
        public Builder setCardCVV(String cardCVV) {
            this.cardCVV = cardCVV;
            return this;
        }


        public Builder copy(Card card) {
            this.cardId = card.cardId;
            this.cardHolderName = card.cardHolderName;
            this.cardType = card.cardType;
            this.cardNumber = card.cardNumber;
            this.cardExpiry = card.cardExpiry;
            this.cardCVV = card.cardCVV;
            return this;
        }
        public Card build() {
            return new Card(this);
        }
    }
}

