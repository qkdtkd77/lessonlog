package com.project.lessonlog.lesson.repository;

import com.project.lessonlog.lesson.domain.LessonNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonNoteRepository extends JpaRepository<LessonNote, Long> {
    Page<LessonNote> findByStudentId(Long studentId, Pageable pageable);

    void deleteByStudentId(Long studentId);

    Page<LessonNote> findByStudentUserIdAndStudentId(Long userId, Long studentId, Pageable pageDetails);

    Optional<LessonNote> findByIdAndStudentUserId(Long lessonId, Long userId);
}
