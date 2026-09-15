package com.project.lessonlog.lesson.service;

import com.project.lessonlog.lesson.domain.LessonNote;
import com.project.lessonlog.lesson.repository.LessonNoteRepository;
import com.project.lessonlog.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LessonNoteServiceImpl implements LessonNoteService {

    private final LessonNoteRepository lessonNoteRepository;
    private final StudentRepository studentRepository;

    @Override
    public LessonNote createLessonNote(LessonNote lessonNote) {
        if (lessonNote.getStudentId() == null) {
            throw new IllegalArgumentException("Student id must not be null");
        }
        studentRepository.findById(lessonNote.getStudentId()).orElseThrow(
                () -> new NoSuchElementException("Student with id " + lessonNote.getStudentId() + " not found"));
        return lessonNoteRepository.save(lessonNote);
    }

    @Override
    public List<LessonNote> getLessonNotes(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student id must not be null");
        }
        studentRepository.findById(studentId).orElseThrow(
                () -> new NoSuchElementException("Student with id " + studentId + " not found"));

        return lessonNoteRepository.findByStudentId(studentId)
                .stream()
                .sorted(Comparator.comparing(LessonNote::getLessonDate).reversed())
                .toList();
    }
}
