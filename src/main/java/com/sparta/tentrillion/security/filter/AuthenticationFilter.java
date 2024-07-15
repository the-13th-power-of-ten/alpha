package com.sparta.tentrillion.security.filter;

import com.sparta.tentrillion.security.principal.UserPrincipal;
import com.sparta.tentrillion.security.service.JwtService;
import com.sparta.tentrillion.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessTokenValue = jwtService.getAccessTokenFromHeader(request);

        if (!StringUtils.hasText(accessTokenValue) || !accessTokenValue.startsWith(JwtService.AUTH_SCHEMA)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.substringToken(accessTokenValue);

        if (!jwtService.isTokenValidate(accessToken)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        User user = jwtService.getUserFromAccessToken(accessToken);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request,response);
    }
}
