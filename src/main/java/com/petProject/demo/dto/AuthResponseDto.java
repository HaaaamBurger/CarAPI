package com.petProject.demo.dto;

import lombok.Builder;

@Builder
public class AuthResponseDto<T> {
    String message;
    T body;
}
