package com.petProject.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto<T> {
    String message;
    T body;
}
