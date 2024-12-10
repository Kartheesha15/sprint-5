package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, String> {
    
    // Custom query to find the exchange rate between two currencies
    ExchangeRate findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);

   
     @Query("SELECT e FROM ExchangeRate e WHERE e.currencyPair = :currencyPair")
    ExchangeRate findByCurrencyPair(@Param("currencyPair") String currencyPair);
}
