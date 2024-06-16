package com.petProject.demo.dto;

import java.util.List;

import com.petProject.demo.model.Car;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private String id;

    @NotBlank
    private String email;
    
    @NotBlank
    private String password;

    private List<Car> cars;

    private String role;
}
