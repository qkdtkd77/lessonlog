package com.project.lessonlog.lesson.repository;

import com.project.lessonlog.lesson.domain.LessonNote;

public interface LessonNoteRepository {
    LessonNote save(LessonNote lessonNote);
}
