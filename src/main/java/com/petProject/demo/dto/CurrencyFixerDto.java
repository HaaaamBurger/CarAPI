package com.petProject.demo.dto;

import com.petProject.demo.common.type.Currencies;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CurrencyFixerDto {
    private Currencies convertedCurrency;
    private long convertedValue;
}
