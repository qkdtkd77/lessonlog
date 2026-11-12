package com.project.lessonlog.user.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.lessonlog.user.UserRole;

import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "email", "name", "role", "createdAt", "deletedAt"})
public record UserDto(Long id, String email, String name, UserRole role,
                      LocalDateTime createdAt, LocalDateTime deletedAt) {
}
