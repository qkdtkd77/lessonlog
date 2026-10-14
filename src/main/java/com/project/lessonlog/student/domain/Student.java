package com.project.lessonlog.student.domain;

import com.project.lessonlog.user.domain.User;
import jakarta.persistence.*;
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
    // TODO: 전화번호 검증 방식 변경
    @Size(min = 13, max = 13, message = "올바르지 않은 전화번호입니다.")
    private String phone;
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Student(User user, String name, String instrument, String phone, String memo) {
        this.user = user;
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

