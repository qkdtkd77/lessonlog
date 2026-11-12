package com.project.lessonlog.student.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "userId", "name", "instrument", "phone", "memo"})
public record StudentDto(Long id, Long userId, String name, String instrument,
                         String phone, String memo) {
}
