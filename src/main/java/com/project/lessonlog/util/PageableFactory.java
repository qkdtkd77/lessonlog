package com.project.lessonlog.util;

import com.project.lessonlog.exception.InvalidPaginationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public final class PageableFactory {
    private PageableFactory() {
    }

    public static Pageable create(Integer pageNumber, Integer pageSize,
                                  String sortBy, String sortOrder, List<String> allowedSortFields) {
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

        if (allowedSortFields != null && (sortBy == null || !allowedSortFields.contains(sortBy))) {
            throw new InvalidPaginationException("지원하지 않는 정렬 기준입니다.");
        }

        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }

    public static Pageable create(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return create(pageNumber, pageSize, sortBy, sortOrder, null);
    }
}
