package com.project.lessonlog.user.docs;

import com.project.lessonlog.security.CustomUserDetails;
import com.project.lessonlog.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "관리자", description = "선생님 계정 관리 API. ADMIN만 사용할 수 있습니다.")
public interface UserControllerDocs {

    @Operation(
            summary = "선생님 목록 조회",
            description = "선생님 목록을 id 오름차순으로 조회합니다. 탈퇴한 선생님도 포함되며 deletedAt으로 구분합니다."
    )
    @ApiResponse(responseCode = "200", description = "선생님 목록 조회 성공")
    @ApiResponse(responseCode = "400", description = "잘못된 페이징 값")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "ADMIN 권한 없음")
    ResponseEntity<UserResponse> getUsers(Integer pageNumber, Integer pageSize);

    @Operation(
            summary = "선생님 탈퇴 처리",
            description = "선생님 계정을 soft delete합니다. 학생과 레슨 일지는 보존되고, 탈퇴한 계정은 로그인과 기존 토큰 사용이 모두 차단됩니다."
    )
    @ApiResponse(responseCode = "204", description = "탈퇴 처리 성공")
    @ApiResponse(responseCode = "400", description = "자기 자신을 탈퇴 처리하려는 경우")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "ADMIN 권한 없음")
    @ApiResponse(responseCode = "404", description = "선생님이 없거나 이미 탈퇴한 경우")
    ResponseEntity<Void> deleteUser(CustomUserDetails customUserDetails, Long userId);
}
