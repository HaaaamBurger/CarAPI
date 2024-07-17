package com.petProject.demo.dto;

import com.petProject.demo.common.type.Currencies;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CurrencyFixerDto {
    private Currencies convertedCurrency;
    private long convertedValue;
}
