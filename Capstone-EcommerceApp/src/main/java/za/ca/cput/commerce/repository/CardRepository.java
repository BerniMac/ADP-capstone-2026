package za.ca.cput.commerce.repository;

/*
Autor:isheanesu chowuraya(223182192)

 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ca.cput.commerce.domain.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
}
