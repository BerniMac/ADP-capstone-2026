package za.ca.cput.commerce.service;

/*
Autor:isheanesu chowuraya(223182192)
12/07/2026
 */

import za.ca.cput.commerce.domain.Card;

import java.util.List;

public interface CardService {

    Card create(Card card);

    Card read(String cardId);

    Card update(Card card);

    void delete(String cardId);

    List<Card> getAll();
}
