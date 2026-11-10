package com.project.lessonlog.auth.service;

import com.project.lessonlog.auth.dto.LoginRequest;
import com.project.lessonlog.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);
}
