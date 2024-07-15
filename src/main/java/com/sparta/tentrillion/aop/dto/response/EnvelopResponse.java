package com.sparta.tentrillion.aop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvelopResponse {

    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String path;

    private EnvelopResponse(Object result, int status, String message) {
        this.result = result;
        this.status = status;
        this.message = message;
    }

    private EnvelopResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public static ResponseEntity<?> wrap(Object result, HttpStatus status, String message) {
        return ResponseEntity
                .status(status == HttpStatus.NO_CONTENT ? HttpStatus.OK : status)
                .body(
                        new EnvelopResponse(
                                result,
                                status.value(),
                                message
                        )
                );
    }

    public static ResponseEntity<?> warpError(HttpStatus status, String message, String path) {
        return ResponseEntity
                .status(status == HttpStatus.NO_CONTENT ? HttpStatus.OK : status)
                .body(
                        new EnvelopResponse(
                                status.value(),
                                message,
                                path
                        )
                );
    }
}
