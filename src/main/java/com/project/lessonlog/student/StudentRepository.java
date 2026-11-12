package com.project.lessonlog.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findByUserIdAndDeletedAtIsNull(Long userId, Pageable pageable);
    
    boolean existsByIdAndUserIdAndDeletedAtIsNull(Long studentId, Long userId);

    Optional<Student> findByIdAndUserIdAndDeletedAtIsNull(Long studentId, Long userId);
}
