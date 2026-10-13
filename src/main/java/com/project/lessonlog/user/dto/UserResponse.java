package com.project.lessonlog.user.dto;

public class UserResponse<T> {
    private T data;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;
}
