package com.project.lessonlog.student.service;

import com.project.lessonlog.exception.StudentNotFoundException;
import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentDto::from)
                .toList();
    }

    @Override
    public StudentDto registerStudent(String name, String instrument, String phone, String memo) {
        Student student = new Student(name, instrument, phone, memo);
        Student saved = studentRepository.save(student);
        return StudentDto.from(saved);
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        studentRepository.delete(student);
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));
        return StudentDto.from(student);
    }

    @Override
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        Student exists = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));

        exists.updateStudent(studentDto.getName(), studentDto.getInstrument(), studentDto.getPhone(), studentDto.getMemo());
        Student updated = studentRepository.save(exists);
        return StudentDto.from(updated);
    }
}
