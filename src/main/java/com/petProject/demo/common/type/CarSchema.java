package com.petProject.demo.common.type;

import com.petProject.demo.dto.CarDto;

import java.util.List;

public interface CarSchema {
    CarDto save(CarDto carDto);
    List<CarDto> getAll();
    CarDto getByCarId(String carId);
    CarDto removeByCarId(String carId);
    CarDto updateByCarId(String carId, CarDto carDto);
}
