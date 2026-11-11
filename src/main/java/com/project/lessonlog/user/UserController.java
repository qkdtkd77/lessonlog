package com.project.lessonlog.user;

import com.project.lessonlog.user.dto.UserRequest;
import com.project.lessonlog.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.lessonlog.config.AppConstant.DEFAULT_PAGE_NUMBER;
import static com.project.lessonlog.config.AppConstant.DEFAULT_PAGE_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/admin/users")
    public ResponseEntity<UserResponse> getUsers(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
    ) {
        UserResponse response = userService.getUsers(pageNumber, pageSize);
        return ResponseEntity.ok(response);
    }
}
