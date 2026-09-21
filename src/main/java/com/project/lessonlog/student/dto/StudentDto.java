package com.project.lessonlog.student.dto;

import com.project.lessonlog.student.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    private String instrument;
    private String phone;
    private String memo;

    public static StudentDto from(Student student) {
        return new StudentDto(student.getId(), student.getName(), student.getInstrument(), student.getPhone(), student.getMemo());
    }
}
