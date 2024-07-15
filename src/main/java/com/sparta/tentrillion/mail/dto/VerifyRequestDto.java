package com.sparta.tentrillion.mail.dto;

import lombok.Data;

@Data
public class VerifyRequestDto {

    private String email;
    private String code;

}
