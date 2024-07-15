package com.sparta.tentrillion.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD REQUEST"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT FOUND"),

    // User
    USER_INACTIVITY(HttpStatus.FORBIDDEN, "USER STATUS IS INACTIVITY"),
    FAIL_AUTHENTICATION(HttpStatus.BAD_REQUEST, "USERNAME OR PASSWORD IS NOT CORRECT"),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // Email
    FAIL_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 이메일입니다."),
    CODE_EXPIRED(HttpStatus.BAD_REQUEST,"만료된 인증 코드입니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "잘못된 코드입니다."),

    //Board

    // Column

    // Card

    // Comment

    ;

    private final HttpStatus status;
    private final String message;
}
