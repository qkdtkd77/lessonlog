package com.project.lessonlog.lesson.service;

import com.project.lessonlog.common.PageResponse;
import com.project.lessonlog.exception.LessonNoteNotFoundException;
import com.project.lessonlog.exception.StudentIdRequiredException;
import com.project.lessonlog.exception.StudentNotFoundException;
import com.project.lessonlog.lesson.domain.LessonNote;
import com.project.lessonlog.lesson.dto.LessonNoteDto;
import com.project.lessonlog.lesson.mapper.LessonNoteMapper;
import com.project.lessonlog.lesson.repository.LessonNoteRepository;
import com.project.lessonlog.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonNoteServiceImpl implements LessonNoteService {

    private final LessonNoteRepository lessonNoteRepository;
    private final StudentRepository studentRepository;
    private final LessonNoteMapper lessonNoteMapper;

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

        return lessonNoteMapper.toDto(saved);
    }

    @Override
    public PageResponse<List<LessonNoteDto>> getLessonNotes(Long studentId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));

        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                sortBy);

        Pageable pageDetails = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<LessonNote> lessonNotePage = lessonNoteRepository.findByStudentId(studentId, pageDetails);
        List<LessonNote> lessonNotes = lessonNotePage.getContent();

        return new PageResponse<>(lessonNoteMapper.toDtoList(lessonNotes),
                lessonNotePage.getNumber() + 1,
                lessonNotePage.getSize(),
                lessonNotePage.getTotalElements(),
                lessonNotePage.getTotalPages(),
                lessonNotePage.isLast()
        );
    }

    @Override
    public LessonNoteDto updateLessonNote(Long lessonId, LessonNoteDto lessonNote) {
        LessonNote exists = lessonNoteRepository.findById(lessonId).orElseThrow(
                () -> new LessonNoteNotFoundException(lessonId));

        exists.updateLessonNote(lessonNote.getLessonContent(), lessonNote.getHomework(), lessonNote.getMemo(), lessonNote.getLessonDate());
        LessonNote saved = lessonNoteRepository.save(exists);

        return lessonNoteMapper.toDto(saved);
    }
}
