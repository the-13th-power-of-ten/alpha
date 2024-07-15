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
    NOT_ADMIN(HttpStatus.BAD_REQUEST, "NOT ADMIN" ),

    // User
    USER_INACTIVITY(HttpStatus.FORBIDDEN, "USER STATUS IS INACTIVITY"),
    FAIL_AUTHENTICATION(HttpStatus.BAD_REQUEST, "USERNAME OR PASSWORD IS NOT CORRECT"),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_MATCHES(HttpStatus.UNAUTHORIZED, "REFRESH TOKEN IS NOT MATCHES"),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "USER IS NOT AUTHORIZED"),

    // Email
    FAIL_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 이메일입니다."),
    CODE_EXPIRED(HttpStatus.BAD_REQUEST,"만료된 인증 코드입니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "잘못된 코드입니다."),

    //Board
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD NOT FOUND."),
    BOARD_NOT_INVITED(HttpStatus.NOT_FOUND, "NOT INVITED TO BOARD."),
    BOARD_ALREADY_INVITED(HttpStatus.BAD_REQUEST, "ALREADY INVITED TO BOARD."),
    CANNOT_INVITE_MYSELF(HttpStatus.BAD_REQUEST, "CANNOT INVITE YOURSELF."),


    // Column

    // Card

    // Comment

    ;
    private final HttpStatus status;
    private final String message;
}
