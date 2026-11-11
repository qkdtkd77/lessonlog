package com.project.lessonlog.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_INPUT(BAD_REQUEST, "입력값 검증에 실패했습니다."),
    INVALID_PARAMETER_TYPE(BAD_REQUEST, "'%s' 파라미터의 값 형식이 올바르지 않습니다."),
    INVALID_REQUEST_BODY(BAD_REQUEST, "요청 본문의 JSON 형식 또는 필드 타입이 올바르지 않습니다."),
    INVALID_PAGE_NUMBER(BAD_REQUEST, "pageNumber는 1 이상이어야 합니다."),
    INVALID_PAGE_SIZE(BAD_REQUEST, "pageSize는 1 이상 30 이하여야 합니다."),
    INVALID_SORT_FIELD(BAD_REQUEST, "허용되지 않은 정렬 기준입니다."),
    INVALID_SORT_ORDER(BAD_REQUEST, "sortOrder는 asc 또는 desc만 가능합니다."),

    SELF_DELETION_NOT_ALLOWED(BAD_REQUEST, "자기 자신은 탈퇴 처리할 수 없습니다."),

    AUTHENTICATION_FAILED(UNAUTHORIZED, "인증에 실패했습니다."),
    LOGIN_FAILED(UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),

    ACCESS_DENIED(FORBIDDEN, "접근 권한이 없습니다."),

    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),
    STUDENT_NOT_FOUND(NOT_FOUND, "학생을 찾을 수 없습니다."),
    LESSON_NOTE_NOT_FOUND(NOT_FOUND, "레슨 일지를 찾을 수 없습니다."),

    EMAIL_DUPLICATED(CONFLICT, "이미 사용 중인 이메일입니다.");

    private final HttpStatus status;
    private final String message;
}
