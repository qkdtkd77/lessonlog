package com.project.lessonlog.lesson.controller;

import com.project.lessonlog.common.dto.PageResponse;
import com.project.lessonlog.lesson.dto.LessonNoteDto;
import com.project.lessonlog.lesson.dto.LessonNoteRequest;
import com.project.lessonlog.lesson.dto.LessonNoteResponse;
import com.project.lessonlog.lesson.mapper.LessonNoteMapper;
import com.project.lessonlog.lesson.service.LessonNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.lessonlog.lesson.config.AppConstant.DEFAULT_PAGE_NUMBER;
import static com.project.lessonlog.lesson.config.AppConstant.SORT_LESSON_NOTE_DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LessonNoteController {

    private final LessonNoteService lessonNoteService;
    private final LessonNoteMapper lessonNoteMapper;

    @PostMapping("/students/{studentId}/lessons")
    public ResponseEntity<LessonNoteResponse<LessonNoteDto>> createLessonNote(@PathVariable Long studentId, @Valid @RequestBody LessonNoteRequest request) {
        LessonNoteDto savedLessonNote = lessonNoteService.createLessonNote(lessonNoteMapper.toCreateDto(studentId, request));
        return ResponseEntity.status(HttpStatus.CREATED).body(new LessonNoteResponse<>(savedLessonNote));
    }

    @GetMapping("/students/{studentId}/lessons")
    public ResponseEntity<PageResponse<List<LessonNoteDto>>> getLessonNote(
            @PathVariable Long studentId,
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "sortOrder", defaultValue = SORT_LESSON_NOTE_DESC) String sortOrder) {

        PageResponse<List<LessonNoteDto>> lessonNotes = lessonNoteService.getLessonNotes(studentId, pageNumber, sortOrder);
        return ResponseEntity.ok().body(lessonNotes);
    }

    @PutMapping("/lessons/{lessonId}")
    public ResponseEntity<LessonNoteResponse<LessonNoteDto>> updateLessonNote(@PathVariable Long lessonId, @Valid @RequestBody LessonNoteRequest request) {
        LessonNoteDto updateDto = lessonNoteMapper.toUpdateDto(request);
        LessonNoteDto updatedLessonNote = lessonNoteService.updateLessonNote(lessonId, updateDto);
        return ResponseEntity.ok().body(new LessonNoteResponse<>(updatedLessonNote));
    }

    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLessonNote(@PathVariable Long lessonId) {
        lessonNoteService.deleteLessonNote(lessonId);
        return ResponseEntity.noContent().build();
    }
}
