package com.petProject.demo.common.job;

import com.petProject.demo.api.Private24ApiService;
import com.petProject.demo.model.Currency;
import com.petProject.demo.repository.Privat24CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Privat24CurrencyJob {
    private final Private24ApiService private24ApiService;
    private final Privat24CurrencyRepository privat24CurrencyRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateCurrencies() {
        List<Currency> apiCurrencies = private24ApiService.executeCurrencies();

        List<Currency> storedCurrencies = private24ApiService.getStoredCurrencies();

        if (storedCurrencies.isEmpty()) {
            apiCurrencies.forEach(currency -> {
                currency.setUpdatedAt(new Date());
            });

            privat24CurrencyRepository.saveAll(apiCurrencies);
        } else {
            apiCurrencies.forEach(currency -> {
                currency.setUpdatedAt(new Date());
            });
            private24ApiService.updateCurrency(apiCurrencies);
        }
    }
}
