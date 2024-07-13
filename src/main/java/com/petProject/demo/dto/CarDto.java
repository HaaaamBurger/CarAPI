package com.petProject.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@Builder
public class CarDto {
    @MongoId
    private String carId;

    @NotBlank
    private String producer;

    @NotNull
    private Short year;

    @NotBlank
    private String model;

    @NotBlank
    private String city;

    @NotNull
    private CarPriceDto price;

    private String owner;
}
