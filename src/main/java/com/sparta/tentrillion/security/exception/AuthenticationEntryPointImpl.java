package com.sparta.tentrillion.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.tentrillion.aop.dto.response.EnvelopResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpStatus status = HttpStatus.FORBIDDEN;
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(
                mapper.writeValueAsString(
                        EnvelopResponse.warpError(
                                status,
                                "로그인 상태를 확인해주세요.",
                                request.getRequestURI()
                        ).getBody()
                )
        );
    }
}
