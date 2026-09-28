package com.project.lessonlog.lesson.repository;

import com.project.lessonlog.lesson.domain.LessonNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonNoteRepository extends JpaRepository<LessonNote, Long> {
    Page<LessonNote> findByStudent_Id(Long studentId, Pageable pageable);
}
