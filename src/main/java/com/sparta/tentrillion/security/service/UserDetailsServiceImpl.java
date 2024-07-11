package com.sparta.tentrillion.security.service;

import com.sparta.tentrillion.security.principal.UserPrincipal;
import com.sparta.tentrillion.user.User;
import com.sparta.tentrillion.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저를 찾지 못했습니다.")
        );

        return new UserPrincipal(user);
    }
}
