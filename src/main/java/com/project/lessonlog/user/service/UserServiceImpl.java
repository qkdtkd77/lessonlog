package com.project.lessonlog.user.service;

import com.project.lessonlog.exception.EmailDuplicatedException;
import com.project.lessonlog.user.domain.User;
import com.project.lessonlog.user.dto.UserRequest;
import com.project.lessonlog.user.mapper.UserMapper;
import com.project.lessonlog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new EmailDuplicatedException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        User userEntity = userMapper.toEntity(userRequest, encodedPassword);
        userRepository.save(userEntity);
    }
}
