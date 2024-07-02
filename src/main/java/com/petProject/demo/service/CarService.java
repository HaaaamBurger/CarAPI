package com.petProject.demo.service;

import com.petProject.demo.api.Private24ApiService;
import com.petProject.demo.common.mapper.CarMapper;
import com.petProject.demo.common.type.CarSchema;
import com.petProject.demo.dto.CarDto;
import com.petProject.demo.model.Car;
import com.petProject.demo.model.Currency;
import com.petProject.demo.repository.CarRepository;
import com.petProject.demo.security.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CarService implements CarSchema {
    private final CarRepository carRepository;

    private final Private24ApiService private24ApiService;

    private final CarMapper carMapper;

    private final CustomUserService customUserService;

    @Transactional
    @Override
    public CarDto save(CarDto carDto) {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = customUserService
                .loadUserByUsername(
                        context
                                .getAuthentication()
                                .getName()
                )
                .getUsername();

        carDto.setOwner(username);

        Car savedCar = carRepository.save(carMapper.fromDto(carDto));
        return carMapper.toDto(savedCar);
    }

    @Override
    public List<CarDto> getAll() {
        List<Currency> currencies = private24ApiService.getCurrencies();

        return carRepository.findAll().stream().map(carMapper::toDto).toList();
    }

    @Override
    public CarDto getByCarId(String carId) {
        Car savedCar = checkCarForExistenceAndThrowNotFound(carId);
        return carMapper.toDto(savedCar);
    }

    @Transactional
    @Override
    public CarDto removeByCarId(String carId) {
        Car savedCar = checkCarForExistenceAndThrowNotFound(carId);
        carRepository.removeCarByCarId(carId);

        return carMapper.toDto(savedCar);
    }

    @Override
    public CarDto updateByCarId(String carId, CarDto carDto) {
        Car savedCar = checkCarForExistenceAndThrowNotFound(carId);

        if (carDto.getCity() != null) savedCar.setCity(carDto.getCity());
        if (carDto.getModel() != null) savedCar.setModel(carDto.getModel());
        if (carDto.getOwner() != null) savedCar.setOwner(carDto.getOwner());
        if (carDto.getYear() != null) savedCar.setYear(carDto.getYear());
        if (carDto.getProducer() != null) savedCar.setProducer(carDto.getProducer());

        carRepository.save(savedCar);

        return carMapper.toDto(savedCar);

    }

    private Car checkCarForExistenceAndThrowNotFound(String carId) {
        return carRepository.findCarByCarId(carId).orElseThrow(() -> new NotFoundException("Car doesn't exist."));
    }
}
