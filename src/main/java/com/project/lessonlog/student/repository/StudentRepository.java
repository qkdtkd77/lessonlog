package com.project.lessonlog.student.repository;

import com.project.lessonlog.student.domain.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);

    List<Student> findAll();

    boolean delete(Long id);

    Optional<Student> findById(Long id);
}
