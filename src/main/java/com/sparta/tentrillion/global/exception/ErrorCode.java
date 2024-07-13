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

    //Board

    // Column

    // Card
    INPUT_TITLE_DESCRIPTION(HttpStatus.BAD_REQUEST, "TITLE OR DESCRIPTION ARE REQUIRED")

    // Comment

    ;

    private final HttpStatus status;
    private final String message;
}
