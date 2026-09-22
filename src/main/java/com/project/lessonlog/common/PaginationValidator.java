package com.project.lessonlog.common;

import com.project.lessonlog.exception.InvalidPaginationException;

import java.util.List;

public class PaginationValidator {
    public static void validate(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder,
            List<String> allowedSortFields
    ) {
        if (pageNumber == null || pageNumber < 1) {
            throw new InvalidPaginationException("페이지 번호는 1 이상이어야 합니다.");
        }

        if (pageSize == null || pageSize < 1) {
            throw new InvalidPaginationException("페이지 크기는 1 이상이어야 합니다.");
        }

        if (sortOrder == null
                || (!sortOrder.equalsIgnoreCase("asc")
                && !sortOrder.equalsIgnoreCase("desc"))) {
            throw new InvalidPaginationException("정렬 방향은 asc 또는 desc만 가능합니다.");
        }

        if (sortBy == null || !allowedSortFields.contains(sortBy)) {
            throw new InvalidPaginationException("지원하지 않는 정렬 기준입니다.");
        }
    }
}
