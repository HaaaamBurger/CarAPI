package com.petProject.demo.common.mapper;

import com.petProject.demo.dto.UserDto;
import com.petProject.demo.model.User;

public class UserMapper {
    public UserDto toDto(User user) {
        return UserDto
        .builder()
        .id(user.getId())
        .email(user.getEmail())
        .password(user.getPassword())
        .cars(user.getCars())
        .build();
    }

    public User fromDto(UserDto userDto) {
        return User
        .builder()
        .id(userDto.getId())
        .email(userDto.getEmail())
        .password(userDto.getPassword())
        .cars(userDto.getCars())
        .build(); 
    }
}
