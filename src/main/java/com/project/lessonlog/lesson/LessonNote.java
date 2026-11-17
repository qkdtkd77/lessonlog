package com.project.lessonlog.lesson;

import com.project.lessonlog.student.Student;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false, length = 1000)
    private String lessonContent;

    @Column(nullable = false, length = 500)
    private String homework;

    @Column(length = 500)
    private String memo;

    @Column(nullable = false)
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
