package com.project.lessonlog.auth.docs;

import com.project.lessonlog.auth.dto.LoginRequest;
import com.project.lessonlog.auth.dto.LoginResponse;
import com.project.lessonlog.user.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "인증", description = "회원가입과 로그인 API")
public interface AuthControllerDocs {

    @SecurityRequirements
    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인하고 액세스 토큰과 사용자 역할을 반환합니다. 토큰 유효기간은 1시간입니다."
    )
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "400", description = "입력값 검증 실패")
    @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치, 탈퇴한 계정")
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

    @SecurityRequirements
    @Operation(
            summary = "회원가입",
            description = "선생님(TEACHER) 계정을 생성합니다. 이메일은 앞뒤 공백을 제거하고 소문자로 저장합니다."
    )
    @ApiResponse(responseCode = "201", description = "회원가입 성공")
    @ApiResponse(responseCode = "400", description = "입력값 검증 실패")
    @ApiResponse(responseCode = "409", description = "이미 사용 중인 이메일")
    ResponseEntity<Void> signup(UserRequest userRequest);
}
