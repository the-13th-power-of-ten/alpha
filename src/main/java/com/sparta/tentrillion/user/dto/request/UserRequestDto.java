package com.sparta.tentrillion.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

    //아이디

    private String username;

    private String password;

    private String email;

    private String nickname;

    private String manager;

}
