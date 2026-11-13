package com.project.lessonlog.lesson.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record LessonNoteRequest(
        @NotBlank(message = "수업 진행 내용을 입력해주세요.")
        @Size(max = 1000, message = "수업 진행 내용은 1000자 이하로 입력해주세요.")
        String lessonContent,

        @NotBlank(message = "숙제 내용을 입력해주세요.")
        @Size(max = 500, message = "숙제는 500자 이하로 입력해주세요.")
        String homework,

        @Size(max = 500, message = "메모는 500자 이하로 입력해주세요.")
        String memo,

        @NotNull(message = "날짜를 입력해주세요.")
        LocalDate lessonDate
) {
}
