package com.petProject.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto<T> {
    private String message;
    private Long users;
    private T body;
}
