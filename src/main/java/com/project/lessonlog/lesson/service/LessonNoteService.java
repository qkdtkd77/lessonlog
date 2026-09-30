package com.project.lessonlog.lesson.service;

import com.project.lessonlog.common.dto.PageResponse;
import com.project.lessonlog.lesson.dto.LessonNoteDto;

import java.util.List;

public interface LessonNoteService {
    LessonNoteDto createLessonNote(LessonNoteDto lessonNoteDto);

    PageResponse<List<LessonNoteDto>> getLessonNotes(Long studentId, Integer pageNumber, String sortOrder);

    LessonNoteDto updateLessonNote(Long lessonId, LessonNoteDto lessonNoteDto);

    void deleteLessonNote(Long lessonId);
}
