package com.petProject.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petProject.demo.dto.UserDto;
import com.petProject.demo.dto.UserResponseDto;
import com.petProject.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<UserResponseDto<List<UserDto>>> getAll(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "size", required = false) String size) {
        List<UserDto> users = userService.getAll(Integer.parseInt(page), Integer.parseInt(size));
        return ResponseEntity
                .ok()
                .body(UserResponseDto.<List<UserDto>>builder()
                        .message("List of users")
                        .users((long) users.size())
                        .body(users)
                        .build()
                );
    }
}
