package service;

/*
Autor:isheanesu chowuraya(223182192)
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.repository.CardRepository;
import za.ca.cput.commerce.service.impl.CardServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.service.CardService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardServiceImplTest {

    @Autowired
    private CardService cardService;

    private static Card buildCard() {
        return new Card.Builder()
                .setCardId("222")
                .build();
    }

    private static final Card card = buildCard();

    @Test
    @Order(1)
    void create() {
        Card created = cardService.create(card);

        assertNotNull(created);
        assertEquals(card.getCardId(), created.getCardId());
    }

    @Test
    @Order(2)
    void read() {
        Card read = cardService.read(card.getCardId());

        assertNotNull(read);
        assertEquals(card.getCardId(), read.getCardId());
    }

    @Test
    @Order(3)
    void update() {
        Card cardUpdated = new Card.Builder()
                .copy(card)
                .setCardId(card.getCardId())
                .build();

        Card updated = cardService.update(cardUpdated);

        assertNotNull(updated);
        assertEquals(cardUpdated.getCardId(), updated.getCardId());
    }

    @Test
    @Order(4)
    void getAll() {
        List<Card> cards = cardService.getAll();

        assertNotNull(cards);
        assertFalse(cards.isEmpty());
    }

    @Test
    @Order(5)
    void delete() {
        cardService.delete(card.getCardId());

        Card deleted = cardService.read(card.getCardId());

        assertNull(deleted);
    }
}