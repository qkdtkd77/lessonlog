package com.project.lessonlog.lesson.service;

import com.project.lessonlog.lesson.domain.LessonNote;

import java.util.List;

public interface LessonNoteService {
    LessonNote createLessonNote(LessonNote lessonNote);

    List<LessonNote> getLessonNotes(Long lessonId);
}
