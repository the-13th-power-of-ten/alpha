package com.sparta.tentrillion.user;

import com.sparta.tentrillion.global.exception.BusinessException;
import com.sparta.tentrillion.global.exception.ErrorCode;
import com.sparta.tentrillion.security.principal.UserPrincipal;
import com.sparta.tentrillion.security.service.JwtService;
import com.sparta.tentrillion.user.dto.request.LoginRequestDto;
import com.sparta.tentrillion.user.dto.request.UserRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Getter
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final static String MANAGER_CODE = "zhemtpwnfdpsmseoajflrkdlTek";

    public User createUser(UserRequestDto userRequestDto) {

        User.Role role = User.Role.USER;
        // 존재 여부 확인
        if (isExist(userRequestDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다");
        }

        // 매니저 권한 부여 확인
        if (isManager(userRequestDto.getManager())) {
            role = User.Role.MANAGER;
        }
        User user = User.builder()
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .email(userRequestDto.getEmail())
                .nickname(userRequestDto.getNickname())
                .role(role)
                .status(User.Status.INACTIVITY)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            User user = findByUsername(userPrincipal.getUsername());

            String accessToken = jwtService.createAccessToken(user);
            String refreshToken = jwtService.createRefreshToken();

            user.updateRefreshToken(refreshToken);

            response.addHeader(HttpHeaders.AUTHORIZATION, accessToken);
            response.addHeader(JwtService.REFRESH_HEADER, refreshToken);
        } catch (DisabledException e) {
            throw new BusinessException(ErrorCode.USER_INACTIVITY);
        } catch (AuthenticationException e) {
            throw new BusinessException(ErrorCode.FAIL_AUTHENTICATION);
        }
    }

    @Transactional
    public void reIssue(String refreshToken, HttpServletResponse response) {
        User user = findByRefreshToken(refreshToken);

        if (!refreshToken.equals(user.getRefreshtoken())) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_MATCHES);
        }

        String newAccessToken = jwtService.createAccessToken(user);
        String newRefreshToken = jwtService.createRefreshToken();

        user.updateRefreshToken(newRefreshToken);

        response.addHeader(HttpHeaders.AUTHORIZATION, newAccessToken);
        response.addHeader(JwtService.REFRESH_HEADER, newRefreshToken);
    }

    @Transactional
    public void logout(User loginUser) {
        User user = findByUsername(loginUser.getUsername());
        user.updateRefreshToken(null);
    }

    private  User findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshtoken(refreshToken).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );
    }

    private boolean isExist(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    private boolean isManager(String managerCode) {
        if (managerCode != null) {
            return managerCode.equals(MANAGER_CODE);
        } else {
            return false;
        }
    }
}