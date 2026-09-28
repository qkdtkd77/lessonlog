package com.project.lessonlog.lesson.domain;

import com.project.lessonlog.student.domain.Student;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private String lessonContent;
    private String homework;
    private String memo;
    private LocalDate lessonDate;

    public LessonNote(Student student, String lessonContent, String homework, String memo, LocalDate lessonDate) {
        this.student = student;
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
