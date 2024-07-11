package com.sparta.tentrillion.comment;

import com.sparta.tentrillion.card.Card;
import com.sparta.tentrillion.global.TimeStamp;
import com.sparta.tentrillion.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name="comments")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
