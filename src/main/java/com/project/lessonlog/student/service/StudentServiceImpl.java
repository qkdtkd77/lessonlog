package com.project.lessonlog.student.service;

import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public void deleteStudent(Long studentId) {
        boolean deleted = studentRepository.delete(studentId);
        if (!deleted) {
            throw new NoSuchElementException("Student not found");
        }
    }
}
