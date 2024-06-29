package com.petProject.demo.controller;

import java.util.List;

import com.petProject.demo.common.type.Roles;
import com.petProject.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.petProject.demo.dto.UserDto;
import com.petProject.demo.dto.ResponseDto;
import com.petProject.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<ResponseDto<List<UserDto>>> getAll() {
        List<UserDto> users = userService.getAll();
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
        UserDto storedUser = userService.save(userDto);
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
    public UserDto getByUserId(@PathVariable String userId) {
        UserDto storedUserById = userService.getByCarId(userId);
        return null;
    }

    @DeleteMapping("/{userId}")
    public UserDto removeByUserId(String userId) {
        return null;
    }

    @PutMapping("/{userId}")
    public UserDto updateByUserId(String userId, UserDto userDto) {
        return null;
    }
}
