package com.sparta.tentrillion.user;

import com.sparta.tentrillion.common.CommonResponse;
import com.sparta.tentrillion.user.dto.request.UserRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<User>> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        CommonResponse<User> response = new CommonResponse<>(
                "회원가입 성공",
                HttpStatus.CREATED.value(),
                userService.createUser(userRequestDto)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}