package com.project.lessonlog.auth.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"accessToken", "tokenType"})
public record LoginResponse(String accessToken, String tokenType) {
}
