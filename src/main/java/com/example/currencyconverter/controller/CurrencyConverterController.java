package com.example.currencyconverter.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.currencyconverter.model.CurrencyConversion;
import com.example.currencyconverter.model.ConversionResult;
import com.example.currencyconverter.service.CurrencyConverterService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/api/v1")
public class CurrencyConverterController {

    private final CurrencyConverterService converterService;

    public CurrencyConverterController(CurrencyConverterService converterService) {
        this.converterService = converterService;
    }

    @GetMapping("/converter")
    public String showConverter(Model model) {
        model.addAttribute("conversion", new CurrencyConversion());
        return "converter";
    }

    @PostMapping("/convert")
    public String convertCurrency(@ModelAttribute CurrencyConversion conversion, Model model) {
        try {
            // Convert BigDecimal to double for service call
            double amount = conversion.getAmount().doubleValue();
            BigDecimal result = BigDecimal.valueOf(converterService.convert(
                amount,
                conversion.getFromCurrency(),
                conversion.getToCurrency()
            ));

            // Set the converted amount and timestamp
            conversion.setConvertedAmount(result);
            conversion.setTimestamp(LocalDateTime.now());

            // Add the conversion result to the model
            model.addAttribute("conversion", conversion);
            return "result";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "converter";
        }
    }

    @GetMapping("/result")
    public String showResult(@ModelAttribute("conversion") ConversionResult conversion, Model model) {
        // Add the conversion result to the model
        model.addAttribute("conversion", conversion);
        return "result";
    }

    @GetMapping("/convert/history")
    public String viewHistory(Model model) {
        model.addAttribute("conversions", converterService.getRecentConversions());
        return "history";
    }
}
