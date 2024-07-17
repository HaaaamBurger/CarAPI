package com.petProject.demo.auth.util;

import com.petProject.demo.api.Private24ApiService;
import com.petProject.demo.common.type.Currencies;
import com.petProject.demo.dto.CarDto;
import com.petProject.demo.dto.CarPriceDto;
import com.petProject.demo.dto.CurrencyFixerDto;
import com.petProject.demo.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
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


        int uahToUsd = (int) Double.parseDouble(usdCurrency.getSale());
        int uahToEur = (int) Double.parseDouble(eurCurrency.getSale());

        CarPriceDto.CarPriceDtoBuilder priceDtoBuilder = CarPriceDto.builder()
                .createdWithCurrency(carDto.getPrice().getCreatedWithCurrency())
                .createdWithValue(carDto.getPrice().getCreatedWithValue());

        if (carDto.getPrice().getCreatedWithCurrency().equals(Currencies.UAH)) {
            priceDtoBuilder
                    .convertedCurrenciesFixerList(List.of(
                            createCurrencyFixerDto(Currencies.USD, (int) (carDto.getPrice().getCreatedWithValue() * uahToUsd)),
                            createCurrencyFixerDto(Currencies.EUR, (int) (carDto.getPrice().getCreatedWithValue() * uahToEur)
                            )));
//            priceDtoBuilder
//                    .firstConvertedValue(createCurrencyFixerDto(Currencies.USD, (int) (carDto.getPrice().getCreatedWithValue() * uahToUsd)))
//                    .secondConvertedValue(createCurrencyFixerDto(Currencies.EUR, (int) (carDto.getPrice().getCreatedWithValue() * uahToEur)));
        } else if (carDto.getPrice().getCreatedWithCurrency().equals(Currencies.USD)) {
            priceDtoBuilder
                    .convertedCurrenciesFixerList(List.of(
                            createCurrencyFixerDto(Currencies.UAH, (int) (carDto.getPrice().getCreatedWithValue() * uahToUsd)),
                            createCurrencyFixerDto(Currencies.EUR, (int) ((carDto.getPrice().getCreatedWithValue() * uahToUsd)) / uahToEur)
                            ));
//            priceDtoBuilder
//                    .firstConvertedValue(createCurrencyFixerDto(Currencies.UAH, (int) (carDto.getPrice().getCreatedWithValue() * uahToUsd)))
//                    .secondConvertedValue(createCurrencyFixerDto(Currencies.EUR, (int) ((carDto.getPrice().getCreatedWithValue() * uahToUsd)) / uahToEur));
        } else if (carDto.getPrice().getCreatedWithCurrency().equals(Currencies.EUR)) {
            priceDtoBuilder
                    .convertedCurrenciesFixerList(List.of(
                            createCurrencyFixerDto(Currencies.UAH, (int) (carDto.getPrice().getCreatedWithValue() * uahToEur)),
                            createCurrencyFixerDto(Currencies.USD, (int) ((carDto.getPrice().getCreatedWithValue() * uahToEur) / uahToUsd)))
                    );
//            priceDtoBuilder
//                    .firstConvertedValue(createCurrencyFixerDto(Currencies.UAH, (int) (carDto.getPrice().getCreatedWithValue() * uahToEur)))
//                    .secondConvertedValue(createCurrencyFixerDto(Currencies.USD, (int) ((carDto.getPrice().getCreatedWithValue() * uahToEur) / uahToUsd)));
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
