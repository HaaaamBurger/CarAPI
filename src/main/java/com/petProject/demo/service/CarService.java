package com.petProject.demo.service;

import com.petProject.demo.api.Private24ApiService;
import com.petProject.demo.auth.util.CarUtil;
import com.petProject.demo.common.mapper.CarMapper;
import com.petProject.demo.common.type.AccountTypes;
import com.petProject.demo.common.type.CarSchema;
import com.petProject.demo.common.type.Currencies;
import com.petProject.demo.dto.CarDto;
import com.petProject.demo.dto.CarPriceDto;
import com.petProject.demo.dto.CurrencyFixerDto;
import com.petProject.demo.dto.UserDto;
import com.petProject.demo.model.Car;
import com.petProject.demo.model.Currency;
import com.petProject.demo.repository.CarRepository;
import com.petProject.demo.security.exception.NotFoundException;
import com.petProject.demo.security.exception.PermissionNotAllowedException;
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

    private final CarMapper carMapper;

    private final CarUtil carUtil;

    private final CustomUserService customUserService;

    private final Private24ApiService private24ApiService;

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

        int carsListSize = carRepository.getAllByOwner(username).size();
        UserDto userByEmail = customUserService.getUserByEmail(username);

        if (carsListSize > 0 && userByEmail.getType().equals(AccountTypes.BASE)) {
            throw new PermissionNotAllowedException("Your account type is Base, so you're not able to sell more than 1 car.");
        }

        carDto.setOwner(username);

//        List<Currency> storedCurrencies = private24ApiService.getStoredCurrencies();
        CarDto carForSet = carUtil.setPriceWithCurrency(carDto);
        Car savedCar = carRepository.save(carMapper.fromDto(carForSet));
        return carMapper.toDto(savedCar);
    }

    //            String usdInUah = storedCurrencies.get(1).getBuy();
//            String eurInUah = storedCurrencies.get(0).getBuy();

//            carDto.setPrice(CarPriceDto
//                    .builder()
//                            .currency(carDto.getPrice().getCurrency())
//                            .value(carDto.getPrice().getValue())
//                            .firstConvertedValue(CurrencyFixerDto
//                                    .builder()
//                                    .convertedCurrency(Currencies.UAH)
//                                    .convertedValue(((int) Math.round(Double.parseDouble(usdInUah)) * carDto.getPrice().getValue()))
//                                    .build()
//                            )
//                            .secondConvertedValue(CurrencyFixerDto
//                                    .builder()
//                                    .convertedCurrency(Currencies.EUR)
//                                    .convertedValue(((int) carDto.getPrice().getValue() / Math.round(Double.parseDouble(eurInUah))))
//                                    .build()
//                            )
//                    .build());

    @Override
    public List<CarDto> getAll() {
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
