package com.sparta.tentrillion.security.service;

import com.sparta.tentrillion.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtService {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;
    @Value("${jwt.access-expire-time}")
    private int accessExpireTime;
    @Value("${jwt.refresh-expire-time}")
    private long refreshExpireTime;
    private SecretKey key;
    public static String REFRESH_HEADER = "RefreshToken";
    public static String AUTH_SCHEMA = "Bearer ";
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(jwtSecretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createAccessToken(User user) {
        Date date = new Date();

        return AUTH_SCHEMA +
                Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(date)
                .expiration(new Date(date.getTime() + accessExpireTime))
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("status", user.getStatus())
                .claim("nickname", user.getNickname())
                .signWith(key)
                .compact();
    }

    public String createRefreshToken() {
        Date date = new Date();

        return AUTH_SCHEMA +
                Jwts.builder()
                .issuedAt(date)
                .expiration(new Date(date.getTime() + refreshExpireTime))
                .signWith(key)
                .compact();
    }

    public String getAccessTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    public String getRefreshTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(REFRESH_HEADER);
    }

    public String substringToken(String token) {
        if (!token.startsWith(AUTH_SCHEMA)) {
            return null;
        }
        return token.substring(AUTH_SCHEMA.length());
    }

    public boolean isTokenValidate(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            log.error("유효하지 않은 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT 토큰입니다.");
        }
        return false;
    }

    public User getUserFromAccessToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return User.builder()
                .id(claims.get("id", Long.class))
                .username(claims.getSubject())
                .email(claims.get("email", String.class))
                .nickname(claims.get("nickname", String.class))
                .role(User.Role.valueOf(claims.get("role", String.class)))
                .status(User.Status.valueOf(claims.get("status", String.class)))
                .build();
    }

}
