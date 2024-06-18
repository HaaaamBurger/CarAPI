package com.petProject.demo.security.handler;

import com.petProject.demo.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RequestExceptionHandlers {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponseDto
                        .builder()
                        .createdAt(new Date())
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getBody().getDetail())
                        .build()
                );
    }


    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ExceptionResponseDto> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponseDto
                        .builder()
                        .createdAt(new Date())
                        .status(HttpStatus.BAD_REQUEST)
                        .message("No body provided...")
                        .build()
                );
    }
}
