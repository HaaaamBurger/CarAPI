package com.petProject.demo.dto;

import com.petProject.demo.common.type.Currencies;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
public class CarPriceDto {
    @NotNull
    private Currencies createdWithCurrency;

    @Min(0)
    @NotNull
    private long createdWithValue;

    private List<CurrencyFixerDto> convertedCurrenciesFixerList;

//    private CurrencyFixerDto convertedCurrency;
//
//    private CurrencyFixerDto convertedCurrencyAdditional;

}
