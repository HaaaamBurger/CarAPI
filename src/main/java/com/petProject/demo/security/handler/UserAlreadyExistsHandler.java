package com.petProject.demo.security.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.petProject.demo.dto.ExceptionResponseDto;
import com.petProject.demo.security.exception.UserAlreadyExistsException;

@ControllerAdvice
public class UserAlreadyExistsHandler {
    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponseDto> userAlreadyExistsHandler(String message) {
        return ResponseEntity
            .badRequest()
            .body(ExceptionResponseDto
                .builder()
                .createdAt(new Date())
                .status(HttpStatus.BAD_REQUEST)
                .message(message)
                .build()
            );
    }
}
