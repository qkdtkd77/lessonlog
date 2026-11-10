package com.project.lessonlog.exception.custom;

import lombok.Getter;

@Getter
public class StudentIdRequiredException extends RuntimeException {
    public StudentIdRequiredException() {
        super("학생 ID는 필수입니다.");
    }
}
