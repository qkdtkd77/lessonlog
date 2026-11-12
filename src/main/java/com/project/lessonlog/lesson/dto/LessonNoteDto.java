package com.project.lessonlog.lesson.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

@JsonPropertyOrder({"id", "studentId", "lessonContent", "homework", "memo", "lessonDate"})
public record LessonNoteDto(Long id, Long studentId, String lessonContent,
                            String homework, String memo, LocalDate lessonDate) {
}
