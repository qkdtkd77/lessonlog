package com.project.lessonlog.lesson.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class LessonNote {
    private final String lessonContent;
    private final String homework;
    private final String memo;
    private final Long studentId;
    private final LocalDate lessonDate;
    @Setter
    private Long id;

    public LessonNote(Long studentId, LocalDate lessonDate, String lessonContent, String homework, String memo) {
        this.studentId = studentId;
        this.lessonDate = lessonDate;
        this.lessonContent = lessonContent;
        this.homework = homework;
        this.memo = memo;
    }
}
