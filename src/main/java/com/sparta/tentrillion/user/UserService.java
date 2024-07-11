package com.sparta.tentrillion.user;

import com.sparta.tentrillion.user.dto.request.UserRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
@RequiredArgsConstructor
public class UserService {

    private final static String MANAGER_CODE = "zhemtpwnfdpsmseoajflrkdlTek";
    private final UserRepository userRepository;

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
                .password(userRequestDto.getPassword())
                .email(userRequestDto.getEmail())
                .nickname(userRequestDto.getNickname())
                .role(role)
                .status(User.Status.INACTIVITY)
                .build();

        return userRepository.save(user);
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