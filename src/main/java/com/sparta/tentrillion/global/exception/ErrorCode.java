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
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT FOUND USER"),
    REFRESH_TOKEN_NOT_MATCHES(HttpStatus.UNAUTHORIZED, "REFRESH TOKEN IS NOT MATCHES"),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "USER IS NOT AUTHORIZED"),
    USERNAME_NOT_FOUND(HttpStatus.FORBIDDEN, "USER NOT FOUND"),
    ALREADY_EXISTING_USER(HttpStatus.BAD_REQUEST, "THE ID ALREAEDY EXISTS."),

    // Email
    FAIL_EMAIL(HttpStatus.BAD_REQUEST, "THIS IS AN INVALID EMAIL"),
    CODE_EXPIRED(HttpStatus.BAD_REQUEST,"THIS VERIFICATION CODE HAS EXPIRED."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "IT'S AN INVALID CODE."),

    //Board
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD NOT FOUND."),
    BOARD_NOT_INVITED(HttpStatus.NOT_FOUND, "NOT INVITED TO BOARD."),
    BOARD_ALREADY_INVITED(HttpStatus.BAD_REQUEST, "ALREADY INVITED TO BOARD."),
    CANNOT_INVITE_MYSELF(HttpStatus.BAD_REQUEST, "CANNOT INVITE YOURSELF."),


    // Column

    // Card
    INPUT_TITLE_DESCRIPTION(HttpStatus.BAD_REQUEST, "TITLE OR DESCRIPTION ARE REQUIRED")

    // Comment

    ;
    private final HttpStatus status;
    private final String message;
}
