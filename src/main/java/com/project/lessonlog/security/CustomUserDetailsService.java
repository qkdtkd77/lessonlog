package com.project.lessonlog.security;

import com.project.lessonlog.user.domain.User;
import com.project.lessonlog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@NullMarked
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email.trim().toLowerCase()).orElseThrow(
                () -> new UsernameNotFoundException("아이디와 비밀번호가 올바르지 않습니다."));

        return new CustomUserDetails(user);
    }
}
