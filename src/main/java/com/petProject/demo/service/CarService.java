package com.petProject.demo.service;

import com.petProject.demo.common.mapper.CarMapper;
import com.petProject.demo.common.type.CarSchema;
import com.petProject.demo.common.type.Cars;
import com.petProject.demo.dto.CarDto;
import com.petProject.demo.model.Car;
import com.petProject.demo.repository.CarRepository;
import com.petProject.demo.security.exception.EntityAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarService implements CarSchema {
    private final CarRepository carRepository;

    private final CarMapper carMapper;

    @Override
    public CarDto save(CarDto carDto) {
        Car savedCar = carRepository.save(carMapper.fromDto(carDto));
        return carMapper.toDto(savedCar);
    }

    @Override
    public List<CarDto> getAll() {
        return carRepository.findAll().stream().map(carMapper::toDto).toList();
    }

    @Override
    public CarDto getByCarId(String carId) {
        Car savedCar = checkCarForExistence(carId);
        return carMapper.toDto(savedCar);
    }

    @Transactional
    @Override
    public CarDto removeByCarId(String carId) {
        Car savedCar = checkCarForExistence(carId);
        carRepository.removeCarByCarId(carId);

        return carMapper.toDto(savedCar);
    }

    @Override
    public CarDto updateByCarId(String carId, CarDto carDto) {
        return null;
//        Car savedCar = checkCarForExistence(carId);
//
//        if (!carDto.getCity().isEmpty()) savedCar.setCity(carDto.getCity());
//
//
//
//        //TODO Iterator for object.
    }

    private Car checkCarForExistence(String carId) {
        return carRepository.findCarByCarId(carId).orElseThrow(() -> new EntityAlreadyExistsException("Car doesn't exist."));
    }
}
