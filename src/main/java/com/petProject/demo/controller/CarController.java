package com.petProject.demo.controller;

import com.petProject.demo.dto.CarDto;
import com.petProject.demo.dto.ResponseDto;
import com.petProject.demo.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @Secured({"SELLER", "ADMIN"})
    @PostMapping()
    public ResponseEntity<ResponseDto<CarDto>> save(@Valid @RequestBody CarDto carDto) {
        CarDto savedCarDto = carService.save(carDto);

        return ResponseEntity
                .ok()
                .body(ResponseDto
                        .<CarDto>builder()
                        .message("Your new car was successfully saved.")
                        .count(1L)
                        .body(savedCarDto)
                        .build()
                );
    }



    @GetMapping()
    public ResponseEntity<ResponseDto<List<CarDto>>> getAll() {
        List<CarDto> carsDto = carService.getAll();

        return ResponseEntity
                .ok()
                .body(ResponseDto
                        .<List<CarDto>>builder()
                        .message("List of cars.")
                        .count((long) carsDto.size())
                        .body(carsDto)
                        .build()
                );
    }


    @GetMapping("/{carId}")
    public ResponseEntity<ResponseDto<CarDto>> getByCarId(@PathVariable String carId) {
        CarDto carById = carService.getByCarId(carId);

        return ResponseEntity
                .ok()
                .body(ResponseDto
                        .<CarDto>builder()
                        .count(1L)
                        .message("Here is your car by ID: %s".formatted(carId))
                        .body(carById)
                        .build());
    }


    @Secured({"SELLER", "ADMIN"})
    @DeleteMapping("/{carId}")
    public ResponseEntity<ResponseDto<CarDto>> removeByCarId(@PathVariable String carId) {
        CarDto removedCarById = carService.removeByCarId(carId);

        return ResponseEntity
                .ok()
                .body(ResponseDto
                        .<CarDto>builder()
                        .count(1L)
                        .message("Removed car by ID: %s".formatted(carId))
                        .body(removedCarById)
                        .build());
    }

    @Secured({"SELLER", "ADMIN"})
    @PutMapping("/{carId}")
    public ResponseEntity<ResponseDto<CarDto>> updateByCarId(@PathVariable String carId, @RequestBody CarDto carDto) {
        CarDto updatedCarById = carService.updateByCarId(carId, carDto);

        return ResponseEntity
                .ok()
                .body(ResponseDto
                        .<CarDto>builder()
                        .count(1L)
                        .message("Updated car by ID: %s".formatted(carId))
                        .body(updatedCarById)
                        .build());
    }
}
