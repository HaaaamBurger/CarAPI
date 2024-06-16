package com.petProject.demo.dto;

import java.util.List;

import com.petProject.demo.model.Car;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private String id;

    private String email;
    
    private String password;

    private List<Car> cars;

    private String role;
}
