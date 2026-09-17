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
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));

        studentRepository.delete(student);
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(
                () -> new NoSuchElementException("Student not found"));
    }

    @Override
    public Student updateStudent(Long studentId, Student student) {
        Student exists = studentRepository.findById(studentId).orElseThrow(
                () -> new NoSuchElementException("Student not found"));

        exists.updateStudent(student.getName(), student.getInstrument(), student.getPhone(), student.getMemo());
        return studentRepository.save(exists);
    }
}
