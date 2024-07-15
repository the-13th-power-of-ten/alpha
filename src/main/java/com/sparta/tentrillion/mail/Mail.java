package com.sparta.tentrillion.mail;

import com.sparta.tentrillion.global.TimeStamp;
import com.sparta.tentrillion.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Mail extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Mail(User user){
        this.user = user;
        this.email = user.getEmail();
        this.code = "initcode";
    }

    public void mailAddCode(String code) {
        this.code = code;
    }


}
