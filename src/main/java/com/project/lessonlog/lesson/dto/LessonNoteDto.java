package com.project.lessonlog.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonNoteDto {
    private Long id;
    private Long studentId;
    private String lessonContent;
    private String homework;
    private String memo;
    private LocalDate lessonDate;
}
