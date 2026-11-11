package com.project.lessonlog.exception.custom;

public class SelfDeletionNotAllowedException extends RuntimeException {
    public SelfDeletionNotAllowedException() {
        super("자기 자신은 탈퇴 처리할 수 없습니다.");
    }
}
