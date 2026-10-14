package com.project.lessonlog.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final Long userId;

    public UserNotFoundException(Long userId) {
        super("유저를 찾을 수 없습니다.");
        this.userId = userId;
    }
}
