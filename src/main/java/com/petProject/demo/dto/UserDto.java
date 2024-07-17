package com.petProject.demo.dto;

import java.util.List;

import com.petProject.demo.common.type.AccountTypes;
import com.petProject.demo.model.Car;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@Builder
public class UserDto {
    @MongoId
    private String userId;

    @NotBlank
//    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    private String email;

    @NotBlank
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)[a-zA-Z\\\\d]{8,12}$")
    private String password;

    private List<Car> cars;

    private String role;

    private AccountTypes type;
}
