package com.project.lessonlog.student.service;

import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.dto.StudentRequest;
import com.project.lessonlog.student.dto.StudentResponse;

public interface StudentService {

    StudentResponse getAllStudents(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    StudentDto registerStudent(StudentRequest studentRequest);

    void deleteStudent(Long studentId);

    StudentDto getStudentById(Long studentId);

    StudentDto updateStudent(Long studentId, StudentRequest studentRequest);
}
