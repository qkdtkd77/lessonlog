package com.project.lessonlog.user.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;

    private String password;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
