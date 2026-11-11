package com.project.lessonlog.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndDeletedAtIsNull(Long userId);

    Page<User> findByRole(UserRole role, Pageable pageable);

    Optional<User> findByIdAndRoleAndDeletedAtIsNull(Long userId, UserRole role);
}
