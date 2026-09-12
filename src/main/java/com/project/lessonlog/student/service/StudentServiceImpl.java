package com.project.lessonlog.student.service;

import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student registerStudent(String name, String instrument, String phone, String memo) {
        return studentRepository.save(new Student(name, instrument, phone, memo));
    }
}
