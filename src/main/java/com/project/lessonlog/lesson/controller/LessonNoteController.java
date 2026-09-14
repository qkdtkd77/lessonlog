package com.project.lessonlog.lesson.controller;

import com.project.lessonlog.lesson.domain.LessonNote;
import com.project.lessonlog.lesson.service.LessonNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LessonNoteController {

    private final LessonNoteService lessonNoteService;

    @PostMapping("/students/{studentId}/lessons")
    public ResponseEntity<LessonNote> createLessonNote(@PathVariable Long studentId, @RequestBody Map<String, String> request) {
        String lessonContent = request.get("lessonContent");
        LocalDate lessonDate = LocalDate.parse(request.get("lessonDate"));
        String homework = request.get("homework");
        String memo = request.get("memo");

        try {
            LessonNote lessonNote = new LessonNote(studentId, lessonDate, lessonContent, homework, memo);
            return ResponseEntity.status(HttpStatus.CREATED).body(lessonNoteService.createLessonNote(lessonNote));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
