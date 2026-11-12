package com.project.lessonlog.lesson.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"lessonNotes", "pageNumber", "pageSize", "totalElements", "totalPages", "lastPage"})
public record LessonNoteResponse(List<LessonNoteDto> lessonNotes, Integer pageNumber, Integer pageSize,
                                 Long totalElements, Integer totalPages, Boolean lastPage) {
}
