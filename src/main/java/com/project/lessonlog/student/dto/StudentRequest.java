package com.project.lessonlog.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "악기를 입력해주세요.")
    private String instrument;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Size(min = 13, max = 13, message = "올바르지 않은 전화번호입니다.")
    private String phone;

    private String memo;
}
