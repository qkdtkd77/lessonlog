package com.project.lessonlog.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudentRequest(
        @NotBlank(message = "이름을 입력해주세요.")
        @Size(max = 20, message = "이름은 20자 이하로 입력해주세요.")
        String name,

        @NotBlank(message = "악기를 입력해주세요.")
        @Size(max = 30, message = "악기는 30자 이하로 입력해주세요.")
        String instrument,

        @NotBlank(message = "전화번호를 입력해주세요.")
        @Size(min = 13, max = 13, message = "올바르지 않은 전화번호입니다.")
        String phone,

        @Size(max = 500)
        String memo
) {
}
