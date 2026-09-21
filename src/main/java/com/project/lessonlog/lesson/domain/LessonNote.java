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

    private Long studentId;
    private String lessonContent;
    private String homework;
    private String memo;
    private LocalDate lessonDate;

    public LessonNote(Long studentId, String lessonContent, String homework, String memo, LocalDate lessonDate) {
        this.studentId = studentId;
        this.lessonDate = lessonDate;
        this.lessonContent = lessonContent;
        this.homework = homework;
        this.memo = memo;
    }

    public void updateLessonNote(String lessonContent, String homework, String memo, LocalDate lessonDate) {
        this.lessonDate = lessonDate;
        this.lessonContent = lessonContent;
        this.homework = homework;
        this.memo = memo;
    }
}
