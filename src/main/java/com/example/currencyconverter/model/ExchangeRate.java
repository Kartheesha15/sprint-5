package com.example.currencyconverter.model;


import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

import jakarta.persistence.Entity;

@Entity
@Table(name = "exchange_rates") 
public class ExchangeRate {
    
    @Id
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal rate;
    private String currencyPair;
    public ExchangeRate() {
    }
    
    public ExchangeRate(String fromCurrency, String toCurrency, BigDecimal rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }
    
    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }
}
