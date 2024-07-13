package com.petProject.demo.auth.util;

import com.petProject.demo.api.Private24ApiService;
import com.petProject.demo.common.type.Currencies;
import com.petProject.demo.dto.CarDto;
import com.petProject.demo.dto.CarPriceDto;
import com.petProject.demo.dto.CurrencyFixerDto;
import com.petProject.demo.model.Car;
import com.petProject.demo.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CarUtil {
    private final Private24ApiService private24ApiService;

    public CarDto setPriceWithCurrency(CarDto carDto, Currencies currency) {
        List<Currency> storedCurrencies = private24ApiService.getStoredCurrencies();

        String usdInUah = storedCurrencies.get(1).getBuy();
        String eurInUah = storedCurrencies.get(0).getBuy();

        switch (currency) {
            case Currencies.UAH -> {
                return currencySetter(carDto, Currencies.USD, usdInUah, Currencies.EUR, eurInUah, '*', '/');
            }
            case Currencies.USD -> {
                return currencySetter(carDto, Currencies.UAH, usdInUah, Currencies.EUR, eurInUah, '*', '*');
            }
            case Currencies.EUR -> {
                return currencySetter(carDto, Currencies.UAH, usdInUah, Currencies.USD, eurInUah, '/', '*');
            }
        }
        return carDto;
    }

    private CarDto currencySetter(CarDto carDto,
                                  Currencies firstConvertedCurrency,
                                  String firstConvertedValue,
                                  Currencies secondConvertedCurrency,
                                  String secondConvertedValue,
                                  char firstComparator,
                                  char secondComparator
                                  ) {

        carDto.setPrice(CarPriceDto
                .builder()
                .currency(carDto.getPrice().getCurrency())
                .value(carDto.getPrice().getValue())
                .firstConvertedValue(CurrencyFixerDto
                        .builder()
                        .convertedCurrency(firstConvertedCurrency)
                        .convertedValue(((int) Math.round(Double.parseDouble(firstConvertedValue)) + firstComparator + carDto.getPrice().getValue()))
                        .build()
                )
                .secondConvertedValue(CurrencyFixerDto
                        .builder()
                        .convertedCurrency(secondConvertedCurrency)
                        .convertedValue(((int) carDto.getPrice().getValue() + secondComparator + Math.round(Double.parseDouble(secondConvertedValue))))
                        .build()
                )
                .build());

        return carDto;
    }
}

