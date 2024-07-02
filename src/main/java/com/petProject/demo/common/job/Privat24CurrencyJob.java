package com.petProject.demo.common.job;

import com.petProject.demo.api.Private24ApiService;
import com.petProject.demo.model.Currency;
import com.petProject.demo.repository.Privat24CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Privat24CurrencyJob {
    private final Private24ApiService private24ApiService;

    private final Privat24CurrencyRepository privat24CurrencyRepository;

//    cron = "0 0 12 * * ?"
    @Scheduled(fixedRate = 10000)
    public void updateCurrencies() {
        List<Currency> currencies = private24ApiService.getCurrencies();
        

    }
}
