package com.project.lessonlog.student.repository;

import com.project.lessonlog.student.domain.Student;

import java.util.List;

public interface StudentRepository {
    Student save(Student student);

    List<Student> findAll();
}
