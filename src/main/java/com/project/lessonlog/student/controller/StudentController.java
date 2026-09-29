package com.project.lessonlog.student.controller;

import com.project.lessonlog.common.dto.PageResponse;
import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.dto.StudentRequest;
import com.project.lessonlog.student.dto.StudentResponse;
import com.project.lessonlog.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.lessonlog.student.config.AppConstant.DEFAULT_PAGE_NUMBER;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<PageResponse<List<StudentDto>>> getStudent(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber) {

        PageResponse<List<StudentDto>> allStudents = studentService.getAllStudents(pageNumber);
        return ResponseEntity.ok().body(allStudents);
    }

    @PostMapping
    public ResponseEntity<StudentResponse<StudentDto>> registerStudent(@Valid @RequestBody StudentRequest request) {
        StudentDto savedStudent = studentService.registerStudent(
                request.getName(), request.getInstrument(), request.getPhone(), request.getMemo());
        return ResponseEntity.status(HttpStatus.CREATED).body(new StudentResponse<>(savedStudent));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponse<StudentDto>> getStudentById(@PathVariable Long studentId) {
        StudentDto studentById = studentService.getStudentById(studentId);
        return ResponseEntity.ok().body(new StudentResponse<>(studentById));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponse<StudentDto>> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentRequest request) {
        StudentDto updatedStudent = studentService.updateStudent(studentId,
                new StudentDto(null, request.getName(), request.getInstrument(), request.getPhone(), request.getMemo()));
        return ResponseEntity.ok().body(new StudentResponse<>(updatedStudent));
    }
}
