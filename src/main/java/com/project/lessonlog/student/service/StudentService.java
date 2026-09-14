package com.project.lessonlog.student.service;

import com.project.lessonlog.student.domain.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student registerStudent(String name, String instrument, String phone, String memo);

    void deleteStudent(Long studentId);

    Student getStudentById(Long studentId);
}
