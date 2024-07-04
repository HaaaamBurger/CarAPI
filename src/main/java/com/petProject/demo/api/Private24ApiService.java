package com.petProject.demo.api;

import com.petProject.demo.model.Currency;
import com.petProject.demo.repository.Privat24CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Private24ApiService {
    private final Privat24CurrencyRepository privat24CurrencyRepository;

    private final static String uri = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";

    public List<Currency> executeCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        Currency[] currencies = restTemplate.getForObject(uri, Currency[].class);

        return Arrays.asList(currencies);
    }

    public List<Currency> getStoredCurrencies() {
        return privat24CurrencyRepository.findAll();
    }

    public void updateCurrency(Currency currency) {
        Currency currencyByCurrencyId = privat24CurrencyRepository.findCurrencyByCurrencyId(currency.getCurrencyId());

        System.out.println(currencyByCurrencyId);
        System.out.println(currency);

//        currencyByCurrencyId.setUpdatedAt(new Date());
//        currencyByCurrencyId.setBaseCcy(currency.getBaseCcy());
//        currencyByCurrencyId.setBuy(currency.getBuy());
//        currencyByCurrencyId.setCcy(currency.getCcy());
//        currencyByCurrencyId.setSale(currency.getSale());
//
//        privat24CurrencyRepository.save(currencyByCurrencyId);
    }
}
