package com.project.lessonlog.lesson.repository;

import com.project.lessonlog.lesson.domain.LessonNote;

import java.util.List;

public interface LessonNoteRepository {
    LessonNote save(LessonNote lessonNote);

    List<LessonNote> findByStudentId(Long studentId);
}
