package com.sparta.tentrillion.stat.repository;

import com.sparta.tentrillion.board.Board;
import com.sparta.tentrillion.stat.entity.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatRepository extends JpaRepository<Stat, Long> {
    Optional<Stat> findByBoardIdAndStatId(Board boardId, Long statId);
}
