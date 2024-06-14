package com.petProject.demo.dto;

import lombok.Builder;

@Builder
public class UserResponseDto<T> {
    private T body;
}
