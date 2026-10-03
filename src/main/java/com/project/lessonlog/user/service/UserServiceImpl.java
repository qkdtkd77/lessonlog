package com.project.lessonlog.user.service;

import com.project.lessonlog.exception.EmailDuplicatedException;
import com.project.lessonlog.user.domain.User;
import com.project.lessonlog.user.dto.UserRequest;
import com.project.lessonlog.user.mapper.UserMapper;
import com.project.lessonlog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new EmailDuplicatedException("Email already exists");
        }

        // TODO: Spring Security 적용 후 PasswordEncoder로 비밀번호 암호화
        User userEntity = userMapper.toEntity(userRequest);
        userRepository.save(userEntity);
    }
}
