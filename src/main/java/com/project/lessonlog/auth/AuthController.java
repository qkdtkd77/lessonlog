package com.project.lessonlog.auth;

import com.project.lessonlog.auth.docs.AuthControllerDocs;
import com.project.lessonlog.auth.dto.LoginRequest;
import com.project.lessonlog.auth.dto.LoginResponse;
import com.project.lessonlog.user.UserService;
import com.project.lessonlog.user.dto.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;
    private final UserService userService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Override
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
