package com.project.lessonlog.lesson.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LessonNoteRequest(
        @NotBlank(message = "수업 진행 내용을 입력해주세요.")
        String lessonContent,

        @NotBlank(message = "숙제 내용을 입력해주세요.")
        String homework,

        String memo,

        @NotNull(message = "날짜를 입력해주세요.")
        LocalDate lessonDate
) {
}
