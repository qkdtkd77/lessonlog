package com.project.lessonlog.lesson.docs;

import com.project.lessonlog.lesson.dto.LessonNoteDto;
import com.project.lessonlog.lesson.dto.LessonNoteRequest;
import com.project.lessonlog.lesson.dto.LessonNoteResponse;
import com.project.lessonlog.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "레슨 일지", description = "학생별 레슨 일지 관리 API. TEACHER만 사용할 수 있습니다.")
public interface LessonNoteControllerDocs {

    @Operation(
            summary = "레슨 일지 목록 조회",
            description = "학생의 레슨 일지 목록을 lessonDate 기준으로 정렬해 조회합니다. 같은 날짜는 id 순으로 정렬합니다. sortOrder 기본값은 desc입니다."
    )
    @ApiResponse(responseCode = "200", description = "레슨 일지 목록 조회 성공")
    @ApiResponse(responseCode = "400", description = "잘못된 페이징 또는 정렬 값")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "TEACHER 권한 없음")
    @ApiResponse(responseCode = "404", description = "학생이 없거나 다른 선생님의 학생인 경우")
    ResponseEntity<LessonNoteResponse> getAllLessonNotes(CustomUserDetails userDetails, Long studentId,
                                                         Integer pageNumber, Integer pageSize, String sortOrder);

    @Operation(
            summary = "레슨 일지 상세 조회",
            description = "레슨 일지 한 건을 조회합니다. 다른 선생님의 일지는 조회할 수 없습니다."
    )
    @ApiResponse(responseCode = "200", description = "레슨 일지 조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "TEACHER 권한 없음")
    @ApiResponse(responseCode = "404", description = "일지가 없거나 다른 선생님의 일지인 경우")
    ResponseEntity<LessonNoteDto> getLessonNoteById(CustomUserDetails userDetails, Long lessonId);

    @Operation(
            summary = "레슨 일지 작성",
            description = "학생의 레슨 일지를 작성합니다. lessonDate는 실제 수업 날짜입니다."
    )
    @ApiResponse(responseCode = "201", description = "레슨 일지 작성 성공")
    @ApiResponse(responseCode = "400", description = "입력값 검증 실패")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "TEACHER 권한 없음")
    @ApiResponse(responseCode = "404", description = "학생이 없거나 다른 선생님의 학생인 경우")
    ResponseEntity<LessonNoteDto> createLessonNote(CustomUserDetails userDetails, Long studentId, LessonNoteRequest request);

    @Operation(
            summary = "레슨 일지 수정",
            description = "레슨 일지의 수업 내용, 숙제, 메모, 수업 날짜를 수정합니다."
    )
    @ApiResponse(responseCode = "200", description = "레슨 일지 수정 성공")
    @ApiResponse(responseCode = "400", description = "입력값 검증 실패")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "TEACHER 권한 없음")
    @ApiResponse(responseCode = "404", description = "일지가 없거나 다른 선생님의 일지인 경우")
    ResponseEntity<LessonNoteDto> updateLessonNote(CustomUserDetails userDetails, Long lessonId, LessonNoteRequest request);

    @Operation(
            summary = "레슨 일지 삭제",
            description = "레슨 일지를 삭제합니다. 삭제한 일지는 복구할 수 없습니다."
    )
    @ApiResponse(responseCode = "204", description = "레슨 일지 삭제 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @ApiResponse(responseCode = "403", description = "TEACHER 권한 없음")
    @ApiResponse(responseCode = "404", description = "일지가 없거나 다른 선생님의 일지인 경우")
    ResponseEntity<Void> deleteLessonNote(CustomUserDetails userDetails, Long lessonId);
}
