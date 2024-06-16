package com.petProject.demo.dto;

import lombok.Builder;

@Builder
public class UserResponseDto<T> {
    private String message;
    private T body;
}
