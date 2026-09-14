package com.project.lessonlog.student.controller;

import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Student> registerStudent(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String instrument = request.get("instrument");
        String phone = request.get("phone");
        String memo = request.get("memo");

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
}
