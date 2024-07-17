package com.petProject.demo.api;

import com.petProject.demo.model.Currency;
import com.petProject.demo.repository.Privat24CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Private24ApiService {
    private final Privat24CurrencyRepository privat24CurrencyRepository;

    private final static String uri = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";

    public List<Currency> executeCurrencies() {
        RestTemplate restTemplate = new RestTemplate();

        return List.of(Objects.requireNonNull(restTemplate.getForObject(uri, Currency[].class)));
    }

    public List<Currency> getStoredCurrencies() {
        List<Currency> currencies = privat24CurrencyRepository.findAll();
        if (currencies.isEmpty()) {
            return executeCurrencies();
        }
        return currencies;

    }

    public void updateCurrency(List<Currency> currencies) {
        privat24CurrencyRepository.deleteAll();
//        Currency currencyByCurrencyId = privat24CurrencyRepository.findCurrencyByCurrencyId(currency.getCurrencyId());
// TODO Update logic for updating currencies. Now it's deleting and gets new one from API.
        privat24CurrencyRepository.saveAll(currencies);
    }
}
