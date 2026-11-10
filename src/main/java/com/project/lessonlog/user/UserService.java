package com.project.lessonlog.user;

import com.project.lessonlog.exception.custom.EmailDuplicatedException;
import com.project.lessonlog.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserRequest userRequest) {
        String normalizedEmail = userRequest.getEmail()
                .trim()
                .toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailDuplicatedException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        User userEntity = userMapper.toEntity(userRequest, normalizedEmail, encodedPassword);
        userRepository.save(userEntity);
    }
}
