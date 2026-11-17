package com.project.lessonlog.auth.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.lessonlog.user.UserRole;

@JsonPropertyOrder({"accessToken", "tokenType", "role"})
public record LoginResponse(String accessToken, String tokenType, UserRole role) {
}
