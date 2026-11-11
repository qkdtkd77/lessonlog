package com.project.lessonlog.user;

import com.project.lessonlog.exception.BusinessException;
import com.project.lessonlog.user.dto.UserRequest;
import com.project.lessonlog.user.dto.UserResponse;
import com.project.lessonlog.util.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.lessonlog.exception.ErrorCode.*;

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
            throw new BusinessException(EMAIL_DUPLICATED);
        }

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        User userEntity = userMapper.toEntity(userRequest, normalizedEmail, encodedPassword);
        userRepository.save(userEntity);
    }

    public UserResponse getUsers(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageableFactory.create(pageNumber, pageSize, "id", "asc");
        Page<User> userPage = userRepository.findByRole(UserRole.TEACHER, pageable);
        List<User> users = userPage.getContent();

        return new UserResponse(userMapper.toUserDtoList(users),
                userPage.getNumber() + 1,
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast());
    }

    @Transactional
    public void deleteUser(Long adminId, Long userId) {
        if (adminId.equals(userId)) {
            throw new BusinessException(SELF_DELETION_NOT_ALLOWED);
        }

        User user = userRepository.findByIdAndRoleAndDeletedAtIsNull(userId, UserRole.TEACHER).orElseThrow(
                () -> new BusinessException(USER_NOT_FOUND));

        user.delete();
    }
}
