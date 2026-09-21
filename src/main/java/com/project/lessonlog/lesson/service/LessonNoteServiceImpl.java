package com.project.lessonlog.lesson.service;

import com.project.lessonlog.exception.LessonNoteNotFoundException;
import com.project.lessonlog.exception.StudentIdRequiredException;
import com.project.lessonlog.exception.StudentNotFoundException;
import com.project.lessonlog.lesson.domain.LessonNote;
import com.project.lessonlog.lesson.repository.LessonNoteRepository;
import com.project.lessonlog.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonNoteServiceImpl implements LessonNoteService {
    private final LessonNoteRepository lessonNoteRepository;
    private final StudentRepository studentRepository;

    @Override
    public LessonNote createLessonNote(LessonNote lessonNote) {
        if (lessonNote.getStudentId() == null) {
            throw new StudentIdRequiredException();
        }
        studentRepository.findById(lessonNote.getStudentId()).orElseThrow(
                () -> new StudentNotFoundException(lessonNote.getStudentId()));
        return lessonNoteRepository.save(lessonNote);
    }

    @Override
    public List<LessonNote> getLessonNotes(Long studentId) {
        studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));

        return lessonNoteRepository.findByStudentIdOrderByLessonDateDesc(studentId);
    }

    @Override
    public LessonNote updateLessonNote(Long lessonId, LessonNote lessonNote) {
        LessonNote exists = lessonNoteRepository.findById(lessonId).orElseThrow(
                () -> new LessonNoteNotFoundException(lessonId));
        exists.updateLessonNote(lessonNote.getLessonDate(), lessonNote.getLessonContent(), lessonNote.getHomework(), lessonNote.getMemo());

        return lessonNoteRepository.save(exists);
    }
}
