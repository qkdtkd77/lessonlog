package com.project.lessonlog.user;

import com.project.lessonlog.user.dto.UserDto;
import com.project.lessonlog.user.dto.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(source = "encodedPassword", target = "password")
    @Mapping(source = "normalizedEmail", target = "email")
    @Mapping(target = "role", constant = "TEACHER")
    User toEntity(UserRequest userRequest, String normalizedEmail, String encodedPassword);
}
