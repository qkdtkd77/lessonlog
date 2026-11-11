package com.project.lessonlog.student.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"students", "pageNumber", "pageSize", "totalElements", "totalPages", "lastPage"})
public record StudentResponse(List<StudentDto> students, Integer pageNumber, Integer pageSize,
                              Long totalElements, Integer totalPages, Boolean lastPage) {
}
