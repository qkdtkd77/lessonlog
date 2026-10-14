package com.project.lessonlog.student.controller;

import com.project.lessonlog.student.config.AppConstant;
import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.dto.StudentRequest;
import com.project.lessonlog.student.dto.StudentResponse;
import com.project.lessonlog.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.lessonlog.student.config.AppConstant.DEFAULT_PAGE_NUMBER;
import static com.project.lessonlog.student.config.AppConstant.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<StudentResponse> getAllStudents(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_STUDENT_BY) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstant.SORT_STUDENT_ASC) String sortOrder) {

        StudentResponse allStudents = studentService.getAllStudents(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(allStudents);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long studentId) {
        StudentDto studentById = studentService.getStudentById(studentId);
        return ResponseEntity.ok(studentById);
    }

    @PostMapping
    public ResponseEntity<StudentDto> registerStudent(@Valid @RequestBody StudentRequest studentRequest) {
        StudentDto savedStudent = studentService.registerStudent(studentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentRequest studentRequest) {
        StudentDto updatedStudent = studentService.updateStudent(studentId, studentRequest);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
