package com.project.lessonlog.user;

import com.project.lessonlog.security.CustomUserDetails;
import com.project.lessonlog.user.docs.UserControllerDocs;
import com.project.lessonlog.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.project.lessonlog.config.AppConstant.DEFAULT_PAGE_NUMBER;
import static com.project.lessonlog.config.AppConstant.DEFAULT_PAGE_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    @Override
    @GetMapping
    public ResponseEntity<UserResponse> getUsers(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
    ) {
        UserResponse response = userService.getUsers(pageNumber, pageSize);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId
    ) {
        userService.deleteUser(customUserDetails.getUserId(), userId);
        return ResponseEntity.noContent().build();
    }
}
