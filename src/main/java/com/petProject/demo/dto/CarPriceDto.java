package com.petProject.demo.dto;

import com.petProject.demo.common.type.Currencies;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class CarPriceDto {
    @NotBlank
    private Currencies currency;

    @Min(0)
    @NotNull
    private long value;

    private CurrencyFixerDto firstConvertedValue;

    private CurrencyFixerDto secondConvertedValue;

}
