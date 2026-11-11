package com.project.lessonlog.user.dto;

import com.project.lessonlog.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
