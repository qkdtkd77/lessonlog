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

import static com.project.lessonlog.lesson.config.AppConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LessonNoteController {

    private final LessonNoteService lessonNoteService;

    @GetMapping("/students/{studentId}/lessons")
    public ResponseEntity<LessonNoteResponse> getAllLessonNotes(
            @PathVariable Long studentId,
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortOrder", defaultValue = SORT_LESSON_NOTE_DESC) String sortOrder) {
        LessonNoteResponse allLessonNotes = lessonNoteService.getAllLessonNotes(
                studentId, pageNumber, pageSize, sortOrder);

        return ResponseEntity.ok(allLessonNotes);
    }

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<LessonNoteDto> getLessonNoteById(@PathVariable Long lessonId) {
        LessonNoteDto lessonNoteDto = lessonNoteService.getLessonNoteById(lessonId);
        return ResponseEntity.ok(lessonNoteDto);
    }


    @PostMapping("/students/{studentId}/lessons")
    public ResponseEntity<LessonNoteDto> createLessonNote(
            @PathVariable Long studentId,
            @Valid @RequestBody LessonNoteRequest request) {
        LessonNoteDto savedLessonNote = lessonNoteService.createLessonNote(studentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLessonNote);
    }

    @PutMapping("/lessons/{lessonId}")
    public ResponseEntity<LessonNoteDto> updateLessonNote(
            @PathVariable Long lessonId,
            @Valid @RequestBody LessonNoteRequest request) {
        LessonNoteDto updatedLessonNote = lessonNoteService.updateLessonNote(lessonId, request);
        return ResponseEntity.ok(updatedLessonNote);
    }

    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLessonNote(@PathVariable Long lessonId) {
        lessonNoteService.deleteLessonNote(lessonId);
        return ResponseEntity.noContent().build();
    }
}
