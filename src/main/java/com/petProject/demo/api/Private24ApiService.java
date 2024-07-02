package com.petProject.demo.api;

import com.petProject.demo.model.Currency;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class Private24ApiService {
    private final static String uri = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";

    public List<Currency> getCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        Currency[] currencies = restTemplate.getForObject(uri, Currency[].class);

        return Arrays.asList(currencies);
    }
}
