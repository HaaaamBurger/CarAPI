package com.petProject.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.petProject.demo.dto.UserDto;
import com.petProject.demo.dto.UserResponseDto;
import com.petProject.demo.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDto<UserDto>> register(@Valid @RequestBody UserDto userDto) {

        UserDto savedUserDto = authService.register(userDto);

        return ResponseEntity
            .ok()
            .body(UserResponseDto.<UserDto>builder() 
                .message("Successfully registered")
                .body(savedUserDto) 
                .build()
            );
    }
}
