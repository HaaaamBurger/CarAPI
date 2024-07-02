package com.petProject.demo.model;

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

    @NotBlank
    private String producer;

    @NotNull
    private Short year;
    
    @NotBlank
    private String model;

    @NotBlank
    private String city;

    private String owner;
}
