package com.project.lessonlog.lesson.service;

import com.project.lessonlog.exception.LessonNoteNotFoundException;
import com.project.lessonlog.exception.StudentNotFoundException;
import com.project.lessonlog.lesson.domain.LessonNote;
import com.project.lessonlog.lesson.dto.LessonNoteDto;
import com.project.lessonlog.lesson.dto.LessonNoteRequest;
import com.project.lessonlog.lesson.dto.LessonNoteResponse;
import com.project.lessonlog.lesson.mapper.LessonNoteMapper;
import com.project.lessonlog.lesson.repository.LessonNoteRepository;
import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.repository.StudentRepository;
import com.project.lessonlog.util.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonNoteServiceImpl implements LessonNoteService {

    private static final String SORTABLE_FIELD = "lessonDate";
    private final LessonNoteRepository lessonNoteRepository;
    private final StudentRepository studentRepository;
    private final LessonNoteMapper lessonNoteMapper;

    @Override
    public LessonNoteResponse getAllLessonNotes(Long userId, Long studentId, Integer pageNumber, Integer pageSize, String sortOrder) {
        if (!studentRepository.existsByIdAndUserId(studentId, userId)) {
            throw new StudentNotFoundException(studentId);
        }
        Pageable pageDetails = PageableFactory.create(pageNumber, pageSize, SORTABLE_FIELD, sortOrder);
        Page<LessonNote> lessonNotePage = lessonNoteRepository.findByStudentUserIdAndStudentId(userId, studentId, pageDetails);
        List<LessonNote> lessonNotes = lessonNotePage.getContent();

        return new LessonNoteResponse(lessonNoteMapper.toDtoList(lessonNotes),
                lessonNotePage.getNumber() + 1,
                lessonNotePage.getSize(),
                lessonNotePage.getTotalElements(),
                lessonNotePage.getTotalPages(),
                lessonNotePage.isLast());
    }

    @Override
    public LessonNoteDto getLessonNoteById(Long userId, Long lessonId) {
        LessonNote lessonNote = findLessonNoteByUserId(userId, lessonId);

        return lessonNoteMapper.toDto(lessonNote);
    }

    @Override
    @Transactional
    public LessonNoteDto createLessonNote(Long userId, Long studentId, LessonNoteRequest request) {
        Student student = findStudentByUserId(userId, studentId);

        LessonNote lessonNote = lessonNoteMapper.createEntityFromRequest(student, request);
        lessonNoteRepository.save(lessonNote);

        return lessonNoteMapper.toDto(lessonNote);
    }

    @Override
    @Transactional
    public LessonNoteDto updateLessonNote(Long userId, Long lessonId, LessonNoteRequest request) {
        LessonNote lessonNote = findLessonNoteByUserId(userId, lessonId);

        lessonNote.updateLessonNote(
                request.getLessonContent(),
                request.getHomework(),
                request.getMemo(),
                request.getLessonDate()
        );

        return lessonNoteMapper.toDto(lessonNote);
    }

    @Override
    @Transactional
    public void deleteLessonNote(Long userId, Long lessonId) {
        LessonNote lessonNote = findLessonNoteByUserId(userId, lessonId);
        lessonNoteRepository.delete(lessonNote);
    }

    private @NonNull LessonNote findLessonNoteByUserId(Long userId, Long lessonId) {
        return lessonNoteRepository.findByIdAndStudentUserId(lessonId, userId).orElseThrow(
                () -> new LessonNoteNotFoundException(lessonId));
    }

    private @NonNull Student findStudentByUserId(Long userId, Long studentId) {
        return studentRepository.findByIdAndUserId(studentId, userId).orElseThrow(
                () -> new StudentNotFoundException(studentId));
    }
}
