package com.petProject.demo.model;

import com.petProject.demo.dto.CarPriceDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@Builder
@Document("CARS")
public class Car {
    @MongoId
    private String carId;

    private String producer;

    private Short year;
    
    private String model;

    private String city;

    private CarPriceDto price;

    private String owner;
}
