package com.petProject.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {
    private String message;
    private Long count;
    private T body;
}
