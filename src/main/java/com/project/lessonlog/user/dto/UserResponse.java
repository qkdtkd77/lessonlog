package com.project.lessonlog.user.dto;

import java.util.List;

public class UserResponse {
    private List<UserDto> users;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean lastPage;
}
