package com.sparta.tentrillion.board;

import com.sparta.tentrillion.global.TimeStamp;
import com.sparta.tentrillion.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "invited_boards",
        uniqueConstraints = {@UniqueConstraint(name = "UniqueBoardAndUsers", columnNames = {"board_id", "sender_id", "receiver_id"})})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitedBoards extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
}
