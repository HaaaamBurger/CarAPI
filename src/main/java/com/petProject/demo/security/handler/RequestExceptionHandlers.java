package com.petProject.demo.security.handler;

import com.petProject.demo.dto.ExceptionResponseDto;
import com.petProject.demo.security.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RequestExceptionHandlers extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponseDto
                        .builder()
                        .createdAt(new Date())
                        .status(HttpStatus.BAD_REQUEST)
                        .message(ex.getBody().getDetail())
                        .build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponseDto
                        .builder()
                        .createdAt(new Date())
                        .status(HttpStatus.BAD_REQUEST)
                        .message("No body provided.")
                        .build()
                );
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponseDto> userAlreadyExistsHandler(EntityAlreadyExistsException exception) {
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponseDto
                        .builder()
                        .createdAt(new Date())
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({UnexpectedUserRoleException.class})
    public ResponseEntity<ExceptionResponseDto> unexpectedUserRoleException(UnexpectedUserRoleException exception) {
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponseDto
                        .builder()
                        .createdAt(new Date())
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({WrongCredentialsException.class})
    public ResponseEntity<ExceptionResponseDto> WrongCredentialsException(WrongCredentialsException exception) {
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponseDto
                        .builder()
                        .createdAt(new Date())
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({TokenExpiredException.class})
    public ResponseEntity<ExceptionResponseDto> tokenExpiredException(TokenExpiredException exception) {
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponseDto
                        .builder()
                        .createdAt(new Date())
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionResponseDto> notFoundException(NotFoundException exception) {
        return ResponseEntity
                .badRequest()
                .body(ExceptionResponseDto
                        .builder()
                        .createdAt(new Date())
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getMessage())
                        .build()
                );
    }
}
