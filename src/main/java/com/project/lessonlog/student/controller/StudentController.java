package com.project.lessonlog.student.controller;

import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudent() {
        List<Student> allStudents = studentService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(allStudents);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> registerStudent(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String instrument = request.get("instrument");
        String phone = request.get("phone");
        String memo = request.get("memo");

        Student savedStudent = studentService.registerStudent(name, instrument, phone, memo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }
}
