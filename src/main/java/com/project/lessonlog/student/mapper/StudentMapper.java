package com.project.lessonlog.student.mapper;

import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.dto.StudentRequest;
import com.project.lessonlog.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(source = "user.id", target = "userId")
    StudentDto toStudentDto(Student student);

    @Mapping(target = "id", ignore = true)
    List<StudentDto> toStudentDtoList(List<Student> students);

    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "request.instrument", target = "instrument")
    @Mapping(source = "request.phone", target = "phone")
    @Mapping(source = "request.memo", target = "memo")
    @Mapping(target = "id", ignore = true)
    Student createEntityFromRequest(User user, StudentRequest request);
}
