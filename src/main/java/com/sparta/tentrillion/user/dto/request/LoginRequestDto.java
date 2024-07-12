package com.sparta.tentrillion.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    private String username;
    private String password;

    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
