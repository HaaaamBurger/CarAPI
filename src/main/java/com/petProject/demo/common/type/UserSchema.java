package com.petProject.demo.common.type;

import com.petProject.demo.dto.CarDto;
import com.petProject.demo.dto.UserDto;

import java.util.List;

public interface UserSchema {
    UserDto save(UserDto userDto);
    List<UserDto> getAll();
    UserDto getByCarId(String carId);
    UserDto removeByCarId(String carId);
    UserDto updateByCarId(String carId, UserDto userDto);
}
