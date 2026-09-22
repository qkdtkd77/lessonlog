package com.project.lessonlog.lesson.mapper;

import com.project.lessonlog.lesson.domain.LessonNote;
import com.project.lessonlog.lesson.dto.LessonNoteDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonNoteMapper {

    LessonNoteDto toDto(LessonNote lessonNote);

    List<LessonNoteDto> toDtoList(List<LessonNote> lessonNotes);
}
