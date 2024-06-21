package com.petProject.demo.controller;

import com.petProject.demo.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.petProject.demo.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponseDto<UserDto>> register(@Valid @RequestBody UserDto userDto) {
        UserDto savedUserDto = authService.register(userDto);

        return ResponseEntity
            .ok()
            .body(AuthResponseDto.<UserDto>builder()
                .message("Successfully registered")
                .body(savedUserDto) 
                .build()
            );
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDto<TokenDto>> login(@RequestBody AuthRequestDto authRequestDto) {
        TokenDto tokens = authService.login(authRequestDto);
        return ResponseEntity
                .ok()
                .body(
                        AuthResponseDto.<TokenDto>builder()
                                .message("Successfully authenticated.")
                                .body(tokens)
                                .build()
                );
    }
}
