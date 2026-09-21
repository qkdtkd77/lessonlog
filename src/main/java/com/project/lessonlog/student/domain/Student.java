package com.project.lessonlog.student.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "악기를 입력해주세요.")
    private String instrument;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Size(min = 13, max = 13, message = "올바르지 않은 전화번호입니다.")
    private String phone;
    private String memo;

    public Student(String name, String instrument, String phone, String memo) {
        this.name = name;
        this.instrument = instrument;
        this.phone = phone;
        this.memo = memo;
    }

    public void updateStudent(String name, String instrument, String phone, String memo) {
        this.name = name;
        this.instrument = instrument;
        this.phone = phone;
        this.memo = memo;
    }
}

