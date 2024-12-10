package com.example.currencyconverter;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.currencyconverter.service.CurrencyConverterService;

public class CurrencyConverterTest {
     @Autowired
    private CurrencyConverterService converterService;

    public void testCache() {
        double firstConversion = converterService.convert(100, "USD", "EUR");
        System.out.println("First conversion result: " + firstConversion);
        
        // Call again with the same parameters; this time, it should use the cache
        double secondConversion = converterService.convert(100, "USD", "EUR");
        System.out.println("Second conversion result (cached): " + secondConversion);
    }

}
