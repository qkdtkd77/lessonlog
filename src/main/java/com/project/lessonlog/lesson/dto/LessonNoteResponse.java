package com.project.lessonlog.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonNoteResponse<T> {
    private T data;
}
