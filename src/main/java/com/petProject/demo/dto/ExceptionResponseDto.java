package com.petProject.demo.dto;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponseDto {
    private Date createdAt;
    private HttpStatus status;
    private String message;
}
