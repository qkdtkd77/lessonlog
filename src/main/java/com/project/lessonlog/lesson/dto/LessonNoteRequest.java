package com.project.lessonlog.lesson.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonNoteRequest {
    @NotBlank(message = "수업 진행 내용을 입력해주세요.")
    private String lessonContent;

    @NotBlank(message = "숙제 내용을 입력해주세요.")
    private String homework;

    private String memo;

    @NotNull(message = "날짜를 입력해주세요.")
    private LocalDate lessonDate;
}
