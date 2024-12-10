package com.example.currencyconverter.controller;

import com.example.currencyconverter.service.CurrencyConverterService;
import com.example.currencyconverter.service.ConversionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private CurrencyConverterService currencyConverterService;

    // Show all favorite conversions
    @GetMapping
    public String getFavorites(Model model) {
        model.addAttribute("favorites", currencyConverterService.getFavoriteConversions());
        return "favorites"; // Thymeleaf template for displaying favorites
    }

    // Add a conversion to favorites
    @PostMapping("/add/{conversionId}")
    public String addToFavorites(@PathVariable String conversionId, Model model) {
        boolean success = currencyConverterService.addToFavorites(conversionId);
        if (success) {
            model.addAttribute("message", "Conversion added to favorites.");
        } else {
            model.addAttribute("message", "Conversion already in favorites or not found.");
        }
        model.addAttribute("favorites", currencyConverterService.getFavoriteConversions());
        return "favorites"; // Refresh the favorites page
    }
}
