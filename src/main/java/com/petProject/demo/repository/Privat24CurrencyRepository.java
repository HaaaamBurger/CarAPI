package com.petProject.demo.repository;

import com.petProject.demo.model.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Privat24CurrencyRepository extends MongoRepository<Currency, String> {
    Currency findCurrencyByCurrencyId(String currencyId);
}
