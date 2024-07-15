package com.sparta.tentrillion.user;

import com.sparta.tentrillion.aop.Envelop;
import com.sparta.tentrillion.global.argumentResolver.annotation.LoginUser;
import com.sparta.tentrillion.security.service.JwtService;
import com.sparta.tentrillion.user.dto.request.LoginRequestDto;
import com.sparta.tentrillion.user.dto.request.UserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDto));
    }

    @Envelop("로그인에 성공했습니다.")
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        userService.login(requestDto, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/reissue")
    public ResponseEntity<Void> reIssue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader(JwtService.REFRESH_HEADER);
        userService.reIssue(refreshToken, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@LoginUser User user) {
        userService.logout(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/manager-test")
    public ResponseEntity<String> getManagertTest(@LoginUser User user) {
        return ResponseEntity.status(HttpStatus.OK).body(user.getRole().toString());
    }
}