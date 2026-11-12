package com.project.lessonlog.user.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"users", "pageNumber", "pageSize", "totalElements", "totalPages", "lastPage"})
public record UserResponse(List<UserDto> users, Integer pageNumber, Integer pageSize,
                           Long totalElements, Integer totalPages, Boolean lastPage) {
}
