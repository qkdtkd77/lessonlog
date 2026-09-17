package com.project.lessonlog.lesson.repository;

import com.project.lessonlog.lesson.domain.LessonNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonNoteRepository extends JpaRepository<LessonNote, Long> {
    List<LessonNote> findByStudentIdOrderByLessonDateDesc(Long studentId);
}
