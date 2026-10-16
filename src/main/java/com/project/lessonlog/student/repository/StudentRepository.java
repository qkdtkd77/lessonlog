package com.project.lessonlog.student.repository;

import com.project.lessonlog.student.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findByUser_Id(Long userId, Pageable pageable);

    Optional<Student> findByIdAndUserId(Long studentId, Long userId);
}
