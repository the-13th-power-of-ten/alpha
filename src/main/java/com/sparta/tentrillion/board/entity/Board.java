package com.sparta.tentrillion.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.tentrillion.board.dto.request.BoardRequestDto;
import com.sparta.tentrillion.global.TimeStamp;
import com.sparta.tentrillion.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "boards")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvitedBoard> invitedBoard;

    public Board(BoardRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.user = user;
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
    }
}


