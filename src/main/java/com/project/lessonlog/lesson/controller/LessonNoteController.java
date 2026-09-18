package com.project.lessonlog.lesson.controller;

import com.project.lessonlog.lesson.domain.LessonNote;
import com.project.lessonlog.lesson.dto.LessonNoteRequest;
import com.project.lessonlog.lesson.service.LessonNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LessonNoteController {

    private final LessonNoteService lessonNoteService;

    @PostMapping("/students/{studentId}/lessons")
    public ResponseEntity<LessonNote> createLessonNote(@PathVariable Long studentId, @Valid @RequestBody LessonNoteRequest request) {
        String lessonContent = request.getLessonContent();
        LocalDate lessonDate = request.getLessonDate();
        String homework = request.getHomework();
        String memo = request.getMemo();

        LessonNote lessonNote = new LessonNote(studentId, lessonDate, lessonContent, homework, memo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lessonNoteService.createLessonNote(lessonNote));
    }

    @GetMapping("/students/{studentId}/lessons")
    public ResponseEntity<List<LessonNote>> getLessonNote(@PathVariable Long studentId) {
        List<LessonNote> lessonNotes = lessonNoteService.getLessonNotes(studentId);
        return ResponseEntity.ok().body(lessonNotes);
    }
}
