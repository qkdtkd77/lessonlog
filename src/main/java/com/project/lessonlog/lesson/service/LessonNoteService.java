package com.project.lessonlog.lesson.service;

import com.project.lessonlog.lesson.dto.LessonNoteDto;
import com.project.lessonlog.lesson.dto.LessonNoteResponse;

public interface LessonNoteService {
    LessonNoteDto createLessonNote(LessonNoteDto lessonNoteDto);

    LessonNoteResponse getAllLessonNotes(Long studentId, Integer pageNumber, Integer pageSize, String sortOrder);

    LessonNoteDto updateLessonNote(Long lessonId, LessonNoteDto lessonNoteDto);

    void deleteLessonNote(Long lessonId);
}
