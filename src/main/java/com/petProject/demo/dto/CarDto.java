package com.petProject.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CarDto {
    private String carId;

    private String producer;

    private Short year;

    private String model;

    private String city;

    private String owner;
}
