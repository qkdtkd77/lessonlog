package com.project.lessonlog.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

    @ExceptionHandler(StudentIdRequiredException.class)
    public ResponseEntity<Map<String, List<String>>> handleStudentIdRequiredException(StudentIdRequiredException e) {
        return ResponseEntity.badRequest().body(Map.of("errors", List.of(e.getMessage())));
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleStudentNotFoundException(StudentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("errors", List.of(e.getMessage())));
    }

    @ExceptionHandler(LessonNoteNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleLessonNoteNotFoundException(LessonNoteNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("errors", List.of(e.getMessage())));
    }

    @ExceptionHandler(InvalidPaginationException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidPaginationException(InvalidPaginationException e) {
        return ResponseEntity.badRequest().body(Map.of("errors", List.of(e.getMessage())));
    }
}
