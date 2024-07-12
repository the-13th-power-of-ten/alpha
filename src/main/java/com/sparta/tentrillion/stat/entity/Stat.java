package com.sparta.tentrillion.stat.entity;

import com.sparta.tentrillion.board.Board;
import com.sparta.tentrillion.card.Card;
import com.sparta.tentrillion.global.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "stats")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stat extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "stat", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Card> cards = new ArrayList<>();


    public void updateStat(String title) {
        this.title =title;
    }
}