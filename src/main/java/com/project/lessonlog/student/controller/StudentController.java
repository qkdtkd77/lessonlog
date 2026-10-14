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
@RequestMapping("/api/users/{userId}/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<StudentResponse> getAllStudents(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_STUDENT_BY) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstant.SORT_STUDENT_ASC) String sortOrder,
            @PathVariable Long userId) {

        StudentResponse allStudents = studentService.getAllStudents(userId, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(allStudents);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long userId, @PathVariable Long studentId) {
        StudentDto studentById = studentService.getStudentById(userId, studentId);
        return ResponseEntity.ok(studentById);
    }

    @PostMapping
    public ResponseEntity<StudentDto> registerStudent(@PathVariable Long userId, @Valid @RequestBody StudentRequest studentRequest) {
        StudentDto savedStudent = studentService.registerStudent(userId, studentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long userId, @PathVariable Long studentId, @Valid @RequestBody StudentRequest studentRequest) {
        StudentDto updatedStudent = studentService.updateStudent(userId, studentId, studentRequest);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long userId, @PathVariable Long studentId) {
        studentService.deleteStudent(userId, studentId);
        return ResponseEntity.noContent().build();
    }
}
