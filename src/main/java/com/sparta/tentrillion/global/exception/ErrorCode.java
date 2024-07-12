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
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER NOT FOUND"),
    FAIL_AUTHENTICATION(HttpStatus.BAD_REQUEST, "USERNAME OR PASSWORD IS NOT CORRECT"),
    REFRESH_TOKEN_NOT_MATCHES(HttpStatus.UNAUTHORIZED, "REFRESH TOKEN IS NOT MATCHES")

    //Board

    // Column

    // Card

    // Comment

    ;

    private final HttpStatus status;
    private final String message;
}
