package com.petProject.demo.repository;

import com.petProject.demo.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {
    Optional<Car> findCarByCarId(String carId);
    Optional<Car> removeCarByCarId(String carId);
}
