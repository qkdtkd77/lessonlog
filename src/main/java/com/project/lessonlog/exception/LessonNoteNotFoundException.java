package com.project.lessonlog.exception;

import lombok.Getter;

@Getter
public class LessonNoteNotFoundException extends RuntimeException {
    private final Long lessonId;

    public LessonNoteNotFoundException(Long lessonId) {
        super("레슨 일지를 찾을 수 없습니다.");
        this.lessonId = lessonId;
    }
}
