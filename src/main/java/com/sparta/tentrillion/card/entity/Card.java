package com.sparta.tentrillion.card.entity;

import com.sparta.tentrillion.card.dto.request.CardRequestDto;
import com.sparta.tentrillion.comment.entity.Comment;
import com.sparta.tentrillion.global.TimeStamp;
import com.sparta.tentrillion.stat.entity.Stat;
import com.sparta.tentrillion.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "cards")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "stat_id")
    private Stat stat;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void updateCard (CardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.dueDate = requestDto.getDueDate();
    }
}