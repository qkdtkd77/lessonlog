package com.project.lessonlog.student.mapper;

import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.dto.StudentDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto toStudentDto(Student student);

    List<StudentDto> toStudentDtoList(List<Student> students);
}
