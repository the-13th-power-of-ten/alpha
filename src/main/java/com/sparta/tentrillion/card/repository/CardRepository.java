package com.sparta.tentrillion.card.repository;

import com.sparta.tentrillion.card.entity.Card;
import com.sparta.tentrillion.stat.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
}
