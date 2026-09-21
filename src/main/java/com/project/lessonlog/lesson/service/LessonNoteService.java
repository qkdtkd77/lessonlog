package com.project.lessonlog.lesson.service;

import com.project.lessonlog.lesson.dto.LessonNoteDto;

import java.util.List;

public interface LessonNoteService {
    LessonNoteDto createLessonNote(LessonNoteDto lessonNoteDto);

    List<LessonNoteDto> getLessonNotes(Long studentId);

    LessonNoteDto updateLessonNote(Long lessonId, LessonNoteDto lessonNoteDto);
}
