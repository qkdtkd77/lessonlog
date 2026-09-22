package com.project.lessonlog.lesson.dto;

import com.project.lessonlog.lesson.domain.LessonNote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonNoteDto {
    private Long id;
    private Long studentId;
    private String lessonContent;
    private String homework;
    private String memo;
    private LocalDate lessonDate;

    public static LessonNoteDto from(LessonNote note) {
        return new LessonNoteDto(note.getId(), note.getStudentId(), note.getLessonContent(),
                note.getHomework(), note.getMemo(), note.getLessonDate());
    }

    public static LessonNoteDto create(Long studentId, String lessonContent, String homework, String memo, LocalDate lessonDate) {
        return new LessonNoteDto(null, studentId, lessonContent, homework, memo, lessonDate);
    }

    public static LessonNoteDto update(String lessonContent, String homework, String memo, LocalDate lessonDate) {
        return new LessonNoteDto(null, null, lessonContent, homework, memo, lessonDate);
    }
}
