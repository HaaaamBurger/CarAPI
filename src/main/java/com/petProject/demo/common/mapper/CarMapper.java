package com.petProject.demo.common.mapper;

import com.petProject.demo.dto.CarDto;
import com.petProject.demo.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {
    public CarDto toDto(Car car) {
        return CarDto
                .builder()
                .carId(car.getCarId())
                .city(car.getCity())
                .model(car.getModel())
                .owner(car.getOwner())
                .producer(car.getProducer())
                .price(car.getPrice())
                .year(car.getYear())
                .build();
    }

    public Car fromDto(CarDto carDto) {
        return Car
                .builder()
                .carId(carDto.getCarId())
                .city(carDto.getCity())
                .model(carDto.getModel())
                .owner(carDto.getOwner())
                .producer(carDto.getProducer())
                .price(carDto.getPrice())
                .year(carDto.getYear())
                .build();
    }
}
