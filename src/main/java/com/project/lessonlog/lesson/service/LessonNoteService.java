package com.project.lessonlog.lesson.service;

import com.project.lessonlog.lesson.dto.LessonNoteDto;
import com.project.lessonlog.lesson.dto.LessonNoteRequest;
import com.project.lessonlog.lesson.dto.LessonNoteResponse;

public interface LessonNoteService {
    LessonNoteResponse getAllLessonNotes(Long userId, Long studentId, Integer pageNumber, Integer pageSize, String sortOrder);

    LessonNoteDto getLessonNoteById(Long userId, Long lessonId);

    LessonNoteDto createLessonNote(Long userId, Long studentId, LessonNoteRequest request);

    LessonNoteDto updateLessonNote(Long userId, Long lessonId, LessonNoteRequest request);

    void deleteLessonNote(Long userId, Long lessonId);
}
