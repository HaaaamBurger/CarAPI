package com.petProject.demo.auth.util;

import com.petProject.demo.api.Private24ApiService;
import com.petProject.demo.common.type.Currencies;
import com.petProject.demo.dto.CarDto;
import com.petProject.demo.dto.CarPriceDto;
import com.petProject.demo.dto.CurrencyFixerDto;
import com.petProject.demo.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CarUtil {
    private final Private24ApiService private24ApiService;

    private static final int EUR_INDEX = 0;
    private static final int USD_INDEX = 1;

    public CarDto setPriceWithCurrency(CarDto carDto) {
        List<Currency> storedCurrencies = private24ApiService.getStoredCurrencies();

        Currency usdCurrency = storedCurrencies.get(USD_INDEX);
        Currency eurCurrency = storedCurrencies.get(EUR_INDEX);

        int uahToUsd = (int) Double.parseDouble(usdCurrency.getBuy());
        int uahToEur = (int) Double.parseDouble(eurCurrency.getBuy());

        CarPriceDto.CarPriceDtoBuilder priceDtoBuilder = CarPriceDto.builder()
                .currency(carDto.getPrice().getCurrency())
                .value(carDto.getPrice().getValue());

        if (carDto.getPrice().getCurrency().equals(Currencies.UAH)) {
            priceDtoBuilder
                    .firstConvertedValue(createCurrencyFixerDto(Currencies.USD, (int) (carDto.getPrice().getValue() * uahToUsd)))
                    .secondConvertedValue(createCurrencyFixerDto(Currencies.EUR, (int) (carDto.getPrice().getValue() * uahToEur)));
        } else if (carDto.getPrice().getCurrency().equals(Currencies.USD)) {
            priceDtoBuilder
                    .firstConvertedValue(createCurrencyFixerDto(Currencies.UAH, (int) (carDto.getPrice().getValue() / uahToUsd)))
                    .secondConvertedValue(createCurrencyFixerDto(Currencies.EUR, (int) (carDto.getPrice().getValue() / uahToEur)));
        } else if (carDto.getPrice().getCurrency().equals(Currencies.EUR)) {
            priceDtoBuilder
                    .firstConvertedValue(createCurrencyFixerDto(Currencies.UAH, (int) (carDto.getPrice().getValue() * uahToEur)))
                    .secondConvertedValue(createCurrencyFixerDto(Currencies.USD, (int) (carDto.getPrice().getValue() * uahToEur)));
        }

        carDto.setPrice(priceDtoBuilder.build());

        return carDto;
    }

    private CurrencyFixerDto createCurrencyFixerDto(Currencies currency, int value) {
        CurrencyFixerDto currencyFixerDto = new CurrencyFixerDto();
        currencyFixerDto.setConvertedValue(value);
        currencyFixerDto.setConvertedCurrency(currency);

        return currencyFixerDto;
    }
}
