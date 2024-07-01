package com.petProject.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.petProject.demo.dto.UserDto;
import com.petProject.demo.dto.ResponseDto;
import com.petProject.demo.service.CustomUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final CustomUserService customUserService;

    @GetMapping()
    public ResponseEntity<ResponseDto<List<UserDto>>> getAll() {
        List<UserDto> users = customUserService.getAll();
        return ResponseEntity
                .ok()
                .body(ResponseDto.<List<UserDto>>builder()
                        .message("List of users.")
                        .count((long) users.size())
                        .body(users)
                        .build()
                );
    }

    @PostMapping()
    public ResponseEntity<ResponseDto<UserDto>> save(@RequestBody UserDto userDto) {
        UserDto storedUser = customUserService.save(userDto);
        return ResponseEntity
                .ok()
                .body(ResponseDto.<UserDto>builder()
                        .count(1L)
                        .message("User was successfully stored.")
                        .body(storedUser)
                        .build()
                );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserDto>> getByUserId(@PathVariable String userId) {
        UserDto userById = customUserService.getByCarId(userId);

        return ResponseEntity
                .ok()
                .body(ResponseDto
                        .<UserDto>builder()
                        .message("Here is your user by ID: %s".formatted(userId))
                        .count(1L)
                        .body(userById)
                        .build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserDto>> removeByUserId(String userId) {
        UserDto removedUserById = customUserService.removeByCarId(userId);

        return ResponseEntity
                .ok()
                .body(ResponseDto
                        .<UserDto>builder()
                        .message("Removed car by ID: %s".formatted(userId))
                        .count(1L)
                        .body(removedUserById)
                        .build());

    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserDto>> updateByUserId(@PathVariable String userId, @RequestBody UserDto userDto) {
        UserDto updatedCarById = customUserService.updateByCarId(userId, userDto);

        return ResponseEntity
                .ok()
                .body(ResponseDto
                        .<UserDto>builder()
                        .message("Removed car by ID: %s".formatted(userId))
                        .count(1L)
                        .body(updatedCarById)
                        .build());
    }
}
