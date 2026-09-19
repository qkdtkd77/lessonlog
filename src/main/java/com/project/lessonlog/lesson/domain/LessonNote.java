package com.project.lessonlog.lesson.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class LessonNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lessonContent;
    private String homework;
    private String memo;
    private Long studentId;
    private LocalDate lessonDate;

    public LessonNote(Long studentId, LocalDate lessonDate, String lessonContent, String homework, String memo) {
        this.studentId = studentId;
        this.lessonDate = lessonDate;
        this.lessonContent = lessonContent;
        this.homework = homework;
        this.memo = memo;
    }

    public void updateLessonNote(LocalDate lessonDate, String lessonContent, String homework, String memo) {
        this.lessonDate = lessonDate;
        this.lessonContent = lessonContent;
        this.homework = homework;
        this.memo = memo;
    }
}
