package com.project.lessonlog.exception;

import lombok.Getter;

@Getter
public class StudentNotFoundException extends RuntimeException {
    private final Long studentId;

    public StudentNotFoundException(Long studentId) {
        super("학생을 찾을 수 없습니다.");
        this.studentId = studentId;
    }
}
