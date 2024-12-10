package com.example.currencyconverter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.currencyconverter.model.CurrencyConversion;
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
        // Add recent conversions to the model
        model.addAttribute("recentConversions", converterService.getRecentConversions());
        return "converter";
    }

    @PostMapping("/convert")
    public String convertCurrency(@ModelAttribute CurrencyConversion conversion, Model model) {
        try {
            double amount = conversion.getAmount().doubleValue();
            // Use caching to fetch the conversion rate and calculate the result
            double result = converterService.convert(
                amount,
                conversion.getFromCurrency(),
                conversion.getToCurrency()
            );

            // Set the converted amount and timestamp
            conversion.setConvertedAmount(BigDecimal.valueOf(result));
            conversion.setTimestamp(LocalDateTime.now());

            // Add both conversion and recent conversions to model
            model.addAttribute("conversion", conversion);
            model.addAttribute("recentConversions", converterService.getRecentConversions());
            return "result";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("recentConversions", converterService.getRecentConversions());
            return "converter";
        }
    }

    @GetMapping("/convert/history")
    public String viewHistory(Model model) {
        model.addAttribute("conversions", converterService.getRecentConversions());
        return "history";
    }

    @PostMapping("/convert/clear-history")
    public String clearHistory() {
        converterService.clearHistory();
        return "redirect:/api/v1/converter";
    }

    @PostMapping("/convert/favorite")
    public String addFavorite(@RequestParam String conversionId, RedirectAttributes redirectAttributes) {
        // Logic to mark the conversion as a favorite
        boolean success = converterService.addToFavorites(conversionId);

        if (success) {
            redirectAttributes.addFlashAttribute("message", "Conversion added to favorites!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to add conversion to favorites.");
        }

        return "redirect:/api/v1/convert/history";
    }

    // New endpoint to view favorite conversions
    @GetMapping("/convert/favorites")
    public String viewFavorites(Model model) {
        model.addAttribute("favorites", converterService.getFavoriteConversions());
        return "favorites";
    }
}
