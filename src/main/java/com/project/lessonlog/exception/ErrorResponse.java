package com.project.lessonlog.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(List<String> errors) {
    
}
