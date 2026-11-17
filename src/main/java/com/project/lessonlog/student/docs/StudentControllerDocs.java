package com.project.lessonlog.student.docs;

import com.project.lessonlog.security.CustomUserDetails;
import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.dto.StudentRequest;
import com.project.lessonlog.student.dto.StudentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "학생", description = "로그인한 선생님의 학생 관리 API. TEACHER만 사용할 수 있습니다.")
public interface StudentControllerDocs {

    @Operation(
            summary = "내 학생 목록 조회",
            description = "로그인한 선생님의 학생 목록을 조회합니다. sortBy는 id 또는 name, sortOrder는 asc 또는 desc를 받습니다. 삭제한 학생은 제외됩니다."
    )
    @ApiResponse(responseCode = "200", description = "학생 목록 조회 성공")
    @ApiResponse(responseCode = "400", description = "잘못된 페이징 또는 정렬 값")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "TEACHER 권한 없음")
    ResponseEntity<StudentResponse> getAllStudents(CustomUserDetails userDetails, Integer pageNumber, Integer pageSize,
                                                   String sortBy, String sortOrder);

    @Operation(
            summary = "학생 상세 조회",
            description = "학생 한 명의 정보를 조회합니다. 다른 선생님의 학생은 조회할 수 없습니다."
    )
    @ApiResponse(responseCode = "200", description = "학생 조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "TEACHER 권한 없음")
    @ApiResponse(responseCode = "404", description = "학생이 없거나 다른 선생님의 학생인 경우")
    ResponseEntity<StudentDto> getStudentById(CustomUserDetails userDetails, Long studentId);

    @Operation(
            summary = "학생 등록",
            description = "로그인한 선생님의 학생을 등록합니다."
    )
    @ApiResponse(responseCode = "201", description = "학생 등록 성공")
    @ApiResponse(responseCode = "400", description = "입력값 검증 실패")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "TEACHER 권한 없음")
    ResponseEntity<StudentDto> registerStudent(CustomUserDetails userDetails, StudentRequest studentRequest);

    @Operation(
            summary = "학생 정보 수정",
            description = "학생의 이름, 악기, 전화번호, 메모를 수정합니다."
    )
    @ApiResponse(responseCode = "200", description = "학생 정보 수정 성공")
    @ApiResponse(responseCode = "400", description = "입력값 검증 실패")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "TEACHER 권한 없음")
    @ApiResponse(responseCode = "404", description = "학생이 없거나 다른 선생님의 학생인 경우")
    ResponseEntity<StudentDto> updateStudent(CustomUserDetails userDetails, Long studentId, StudentRequest studentRequest);

    @Operation(
            summary = "학생 삭제",
            description = "학생을 soft delete합니다. 레슨 일지는 보존되지만 삭제한 학생과 그 일지는 더 이상 조회되지 않습니다."
    )
    @ApiResponse(responseCode = "204", description = "학생 삭제 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "TEACHER 권한 없음")
    @ApiResponse(responseCode = "404", description = "학생이 없거나 다른 선생님의 학생인 경우")
    ResponseEntity<Void> deleteStudent(CustomUserDetails userDetails, Long studentId);
}
