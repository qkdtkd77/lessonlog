package com.project.lessonlog.lesson.mapper;

import com.project.lessonlog.lesson.domain.LessonNote;
import com.project.lessonlog.lesson.dto.LessonNoteDto;
import com.project.lessonlog.lesson.dto.LessonNoteRequest;
import com.project.lessonlog.student.domain.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonNoteMapper {

    @Mapping(source = "student.id", target = "studentId")
    LessonNoteDto toDto(LessonNote lessonNote);

    List<LessonNoteDto> toDtoList(List<LessonNote> lessonNotes);

    @Mapping(source = "studentId", target = "studentId")
    @Mapping(target = "id", ignore = true)
    LessonNoteDto toCreateDto(Long studentId, LessonNoteRequest lessonNoteRequest);

    @Mapping(target = "studentId", ignore = true)
    @Mapping(target = "id", ignore = true)
    LessonNoteDto toUpdateDto(LessonNoteRequest lessonNoteRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "student", target = "student")
    @Mapping(source = "lessonNoteDto.memo", target = "memo")
    LessonNote toEntity(Student student, LessonNoteDto lessonNoteDto);
}
