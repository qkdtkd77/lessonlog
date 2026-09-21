package com.project.lessonlog.lesson.service;

import com.project.lessonlog.exception.LessonNoteNotFoundException;
import com.project.lessonlog.exception.StudentIdRequiredException;
import com.project.lessonlog.exception.StudentNotFoundException;
import com.project.lessonlog.lesson.domain.LessonNote;
import com.project.lessonlog.lesson.dto.LessonNoteDto;
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
    public LessonNoteDto createLessonNote(LessonNoteDto lessonNoteDto) {
        if (lessonNoteDto.getStudentId() == null) {
            throw new StudentIdRequiredException();
        }
        studentRepository.findById(lessonNoteDto.getStudentId()).orElseThrow(
                () -> new StudentNotFoundException(lessonNoteDto.getStudentId()));

        LessonNote lessonNote = new LessonNote(
                lessonNoteDto.getStudentId(),
                lessonNoteDto.getLessonContent(),
                lessonNoteDto.getHomework(),
                lessonNoteDto.getMemo(),
                lessonNoteDto.getLessonDate());
        LessonNote saved = lessonNoteRepository.save(lessonNote);

        return LessonNoteDto.from(saved);
    }

    @Override
    public List<LessonNoteDto> getLessonNotes(Long studentId) {
        studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));

        return lessonNoteRepository.findByStudentIdOrderByLessonDateDesc(studentId)
                .stream()
                .map(LessonNoteDto::from)
                .toList();
    }

    @Override
    public LessonNoteDto updateLessonNote(Long lessonId, LessonNoteDto lessonNote) {
        LessonNote exists = lessonNoteRepository.findById(lessonId).orElseThrow(
                () -> new LessonNoteNotFoundException(lessonId));

        exists.updateLessonNote(lessonNote.getLessonContent(), lessonNote.getHomework(), lessonNote.getMemo(), lessonNote.getLessonDate());
        LessonNote saved = lessonNoteRepository.save(exists);
        return LessonNoteDto.from(saved);
    }
}
