package za.ca.cput.commerce.service.impl;

/*
Autor:isheanesu chowuraya(223182192)
12/07/2026
 */
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Card;
import za.ca.cput.commerce.repository.CardRepository;
import za.ca.cput.commerce.service.CardService;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository repository;

    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Card create(Card card) {
        return repository.save(card);
    }

    @Override
    public Card read(String cardId) {
        return repository.findById(cardId).orElse(null);
    }

    @Override
    public Card update(Card card) {

        if (!repository.existsById(card.getCardId())) {
            return null;
        }

        return repository.save(card);
    }

    @Override
    public void delete(String cardId) {
        repository.deleteById(cardId);
    }

    @Override
    public List<Card> getAll() {
        return repository.findAll();
    }
}
