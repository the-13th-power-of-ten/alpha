package com.sparta.tentrillion.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.tentrillion.board.entity.Board;
import com.sparta.tentrillion.global.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = true)
    private String email;

    @Column(unique = true, nullable = true)
    private String nickname;

    @Column
    private String refreshtoken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Board> boards;

    public void updateRefreshToken(String refreshToken) {
        this.refreshtoken = refreshToken;
    }

    public boolean isActivity() {
        return this.status == Status.ACTIVITY;
    }

    public enum Role {
        USER,
        MANAGER
    }

    public enum Status {
        ACTIVITY,
        INACTIVITY
    }
}
