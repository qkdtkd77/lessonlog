package com.project.lessonlog.util;

import com.project.lessonlog.exception.BusinessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.project.lessonlog.exception.ErrorCode.*;

public final class PageableFactory {

    private static final int MAX_PAGE_SIZE = 30;

    private PageableFactory() {
    }

    public static Pageable create(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        validate(pageNumber, pageSize, sortOrder);

        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder)
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }

    public static Pageable createWithTieBreaker(Integer pageNumber, Integer pageSize, String sortBy,
                                                String sortOrder, List<String> allowedSortFields, String tieBreaker) {

        validate(pageNumber, pageSize, sortOrder);

        if (allowedSortFields != null && (sortBy == null || !allowedSortFields.contains(sortBy))) {
            throw new BusinessException(INVALID_SORT_FIELD);
        }

        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder)
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, sortBy);

        if (!tieBreaker.equals(sortBy)) {
            sort = sort.and(Sort.by(Sort.Direction.ASC, tieBreaker));
        }

        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }

    private static void validate(Integer pageNumber, Integer pageSize, String sortOrder) {
        if (pageNumber == null || pageNumber < 1) {
            throw new BusinessException(INVALID_PAGE_NUMBER);
        }

        if (pageSize == null || pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            throw new BusinessException(INVALID_PAGE_SIZE);
        }

        if (!"asc".equalsIgnoreCase(sortOrder) && !"desc".equalsIgnoreCase(sortOrder)) {
            throw new BusinessException(INVALID_SORT_ORDER);
        }
    }
}