package com.petProject.demo.common.mapper;

import org.springframework.stereotype.Component;

import com.petProject.demo.dto.UserDto;
import com.petProject.demo.model.User;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return UserDto
                .builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .cars(user.getCars())
                .type(user.getType())
                .build();
    }

    public User fromDto(UserDto userDto) {
        return User
                .builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .cars(userDto.getCars())
                .type(userDto.getType())
                .build();
    }
}
