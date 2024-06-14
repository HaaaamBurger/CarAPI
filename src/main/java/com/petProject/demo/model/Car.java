package com.petProject.demo.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Document("CARS")
public class Car {
    @NotBlank
    private String producer;

    @NotBlank
    private Short year;
    
    @NotBlank
    private String model;

    @DBRef
    private User owner;
}
