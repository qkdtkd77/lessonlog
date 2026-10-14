package com.project.lessonlog.lesson.service;

import com.project.lessonlog.lesson.dto.LessonNoteDto;
import com.project.lessonlog.lesson.dto.LessonNoteRequest;
import com.project.lessonlog.lesson.dto.LessonNoteResponse;

public interface LessonNoteService {
    LessonNoteDto createLessonNote(Long studentId, LessonNoteRequest request);

    LessonNoteResponse getAllLessonNotes(Long studentId, Integer pageNumber, Integer pageSize, String sortOrder);

    LessonNoteDto updateLessonNote(Long lessonId, LessonNoteRequest request);

    void deleteLessonNote(Long lessonId);

    LessonNoteDto getLessonNoteById(Long lessonId);
}
