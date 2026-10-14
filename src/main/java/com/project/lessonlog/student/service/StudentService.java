package com.project.lessonlog.student.service;

import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.dto.StudentRequest;
import com.project.lessonlog.student.dto.StudentResponse;

public interface StudentService {

    StudentResponse getAllStudents(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    StudentDto registerStudent(Long userId, StudentRequest studentRequest);

    void deleteStudent(Long userId, Long studentId);

    StudentDto getStudentById(Long userId, Long studentId);

    StudentDto updateStudent(Long userId, Long studentId, StudentRequest studentRequest);
}
