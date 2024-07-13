package com.petProject.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    @Secured("ADMIN")
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

    @Secured("ADMIN")
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

    @Secured("ADMIN")
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserDto>> getByUserId(@PathVariable String userId) {
        UserDto userById = customUserService.getByUserId(userId);

        return ResponseEntity
                .ok()
                .body(ResponseDto
                        .<UserDto>builder()
                        .message("Here is your user by ID: %s".formatted(userId))
                        .count(1L)
                        .body(userById)
                        .build());
    }

    @Secured("ADMIN")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserDto>> removeByUserId(String userId) {
        UserDto removedUserById = customUserService.removeByUserId(userId);

        return ResponseEntity
                .ok()
                .body(ResponseDto
                        .<UserDto>builder()
                        .message("Removed car by ID: %s".formatted(userId))
                        .count(1L)
                        .body(removedUserById)
                        .build());

    }

    @Secured("ADMIN")
    @PutMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserDto>> updateByUserId(@PathVariable String userId, @RequestBody UserDto userDto) {
        UserDto updatedCarById = customUserService.updateByUserId(userId, userDto);

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
