package com.project.lessonlog.lesson.service;

import com.project.lessonlog.exception.LessonNoteNotFoundException;
import com.project.lessonlog.exception.StudentIdRequiredException;
import com.project.lessonlog.exception.StudentNotFoundException;
import com.project.lessonlog.lesson.domain.LessonNote;
import com.project.lessonlog.lesson.dto.LessonNoteDto;
import com.project.lessonlog.lesson.dto.LessonNoteResponse;
import com.project.lessonlog.lesson.mapper.LessonNoteMapper;
import com.project.lessonlog.lesson.repository.LessonNoteRepository;
import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.repository.StudentRepository;
import com.project.lessonlog.util.PageableFactory;
import lombok.RequiredArgsConstructor;
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
    public LessonNoteResponse getAllLessonNotes(Long studentId, Integer pageNumber, Integer pageSize, String sortOrder) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }
        Pageable pageDetails = PageableFactory.create(pageNumber, pageSize, SORTABLE_FIELD, sortOrder);
        Page<LessonNote> lessonNotePage = lessonNoteRepository.findByStudent_Id(studentId, pageDetails);
        List<LessonNote> lessonNotes = lessonNotePage.getContent();

        return new LessonNoteResponse(lessonNoteMapper.toDtoList(lessonNotes),
                lessonNotePage.getNumber() + 1,
                lessonNotePage.getSize(),
                lessonNotePage.getTotalElements(),
                lessonNotePage.getTotalPages(),
                lessonNotePage.isLast());
    }

    @Override
    @Transactional
    public LessonNoteDto createLessonNote(LessonNoteDto lessonNoteDto) {
        if (lessonNoteDto.getStudentId() == null) {
            throw new StudentIdRequiredException();
        }
        Student student = studentRepository.findById(lessonNoteDto.getStudentId()).orElseThrow(
                () -> new StudentNotFoundException(lessonNoteDto.getStudentId()));

        LessonNote lessonNote = lessonNoteMapper.toEntity(student, lessonNoteDto);
        LessonNote saved = lessonNoteRepository.save(lessonNote);

        return lessonNoteMapper.toDto(saved);
    }

    @Override
    @Transactional
    public LessonNoteDto updateLessonNote(Long lessonId, LessonNoteDto lessonNote) {
        LessonNote exists = lessonNoteRepository.findById(lessonId).orElseThrow(
                () -> new LessonNoteNotFoundException(lessonId));

        exists.updateLessonNote(lessonNote.getLessonContent(), lessonNote.getHomework(), lessonNote.getMemo(), lessonNote.getLessonDate());
        return lessonNoteMapper.toDto(exists);
    }

    @Override
    @Transactional
    public void deleteLessonNote(Long lessonId) {
        LessonNote lessonNote = lessonNoteRepository.findById(lessonId).orElseThrow(
                () -> new LessonNoteNotFoundException(lessonId));
        lessonNoteRepository.delete(lessonNote);
    }
}
