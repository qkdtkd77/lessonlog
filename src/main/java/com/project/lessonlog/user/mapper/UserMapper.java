package com.project.lessonlog.user.mapper;

import com.project.lessonlog.user.domain.User;
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
    User toEntity(UserRequest userRequest);
}
