package com.project.lessonlog.student.controller;

import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.dto.StudentRequest;
import com.project.lessonlog.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getStudent() {
        List<Student> allStudents = studentService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(allStudents);
    }

    @PostMapping
    public ResponseEntity<Student> registerStudent(@Valid @RequestBody StudentRequest request) {
        String name = request.getName();
        String instrument = request.getInstrument();
        String phone = request.getPhone();
        String memo = request.getMemo();

        Student savedStudent = studentService.registerStudent(name, instrument, phone, memo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        try {
            Student studentById = studentService.getStudentById(studentId);
            return ResponseEntity.ok().body(studentById);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentRequest request) {
        String name = request.getName();
        String instrument = request.getInstrument();
        String phone = request.getPhone();
        String memo = request.getMemo();

        try {
            return ResponseEntity.ok().body(studentService.updateStudent(studentId, new Student(name, instrument, phone, memo)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
