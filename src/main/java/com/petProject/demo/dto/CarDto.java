package com.petProject.demo.dto;

import com.petProject.demo.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarDto {
    private String carId;

    private String producer;

    private Short year;

    private String model;

    private String city;

    private User owner;
}
