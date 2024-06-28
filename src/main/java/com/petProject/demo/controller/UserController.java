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
    public UserDto save(UserDto userDto) {

        return null;
    }

    @GetMapping("/{userId}")
    public UserDto getByUserId(@PathVariable Long userId) {
        return null;
    }

    @DeleteMapping("/{userId}")
    public UserDto removeByUserId(Long userId) {
        return null;
    }

    @PutMapping("/{userId}")
    public UserDto updateByUserId(Long userId, UserDto userDto) {
        return null;
    }
}
