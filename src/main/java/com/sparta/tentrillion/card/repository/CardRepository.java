package com.sparta.tentrillion.card.repository;

import com.sparta.tentrillion.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
