package com.project.lessonlog.lesson.controller;

import com.project.lessonlog.lesson.dto.LessonNoteDto;
import com.project.lessonlog.lesson.dto.LessonNoteRequest;
import com.project.lessonlog.lesson.dto.LessonNoteResponse;
import com.project.lessonlog.lesson.service.LessonNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LessonNoteController {

    private final LessonNoteService lessonNoteService;

    @PostMapping("/students/{studentId}/lessons")
    public ResponseEntity<LessonNoteResponse<LessonNoteDto>> createLessonNote(@PathVariable Long studentId, @Valid @RequestBody LessonNoteRequest request) {
        LessonNoteDto savedLessonNote = lessonNoteService.createLessonNote(LessonNoteDto.create(
                studentId, request.getLessonContent(), request.getHomework(), request.getMemo(), request.getLessonDate()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new LessonNoteResponse<>(savedLessonNote));
    }

    @GetMapping("/students/{studentId}/lessons")
    public ResponseEntity<LessonNoteResponse<List<LessonNoteDto>>> getLessonNote(@PathVariable Long studentId) {
        List<LessonNoteDto> lessonNotes = lessonNoteService.getLessonNotes(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(new LessonNoteResponse<>(lessonNotes));
    }

    @PutMapping("/lessons/{lessonId}")
    public ResponseEntity<LessonNoteResponse<LessonNoteDto>> updateLessonNote(@PathVariable Long lessonId, @Valid @RequestBody LessonNoteRequest request) {
        LessonNoteDto lessonNoteDto = LessonNoteDto.update(
                request.getLessonContent(), request.getHomework(), request.getMemo(), request.getLessonDate());
        LessonNoteDto updatedLessonNote = lessonNoteService.updateLessonNote(lessonId, lessonNoteDto);
        return ResponseEntity.ok().body(new LessonNoteResponse<>(updatedLessonNote));
    }
}
