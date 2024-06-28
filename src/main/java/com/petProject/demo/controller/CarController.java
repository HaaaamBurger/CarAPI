package com.petProject.demo.controller;

import com.petProject.demo.dto.CarDto;
import com.petProject.demo.dto.ResponseDto;
import com.petProject.demo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @PostMapping()
    public ResponseEntity<ResponseDto<CarDto>> save(@RequestBody CarDto carDto) {
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
}
