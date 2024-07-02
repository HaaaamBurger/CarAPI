package com.petProject.demo.dto;

import com.petProject.demo.common.type.Currencies;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CurrencyDto {
    @NotNull
    private Date updatedAt;

    @NotNull
    private Currencies ccy_first;

    @NotNull
    private Currencies ccy_second;

    @NotNull
    private Currencies base_ccy;

    @NotNull
    private float value;
}
