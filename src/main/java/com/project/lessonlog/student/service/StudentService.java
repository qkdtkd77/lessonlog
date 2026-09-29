package com.project.lessonlog.student.service;

import com.project.lessonlog.common.dto.PageResponse;
import com.project.lessonlog.student.dto.StudentDto;

import java.util.List;

public interface StudentService {

    PageResponse<List<StudentDto>> getAllStudents(Integer pageNumber);

    StudentDto registerStudent(String name, String instrument, String phone, String memo);

    void deleteStudent(Long studentId);

    StudentDto getStudentById(Long studentId);

    StudentDto updateStudent(Long studentId, StudentDto studentDto);
}
