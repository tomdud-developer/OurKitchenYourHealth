package com.ourkitchen.yourhealth.mealsmicroservice.controller;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Substance;
import com.ourkitchen.yourhealth.mealsmicroservice.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/substances")
@RequiredArgsConstructor
public class SubstancesController {
    private final MealService mealService;
    @PostMapping
    public Substance saveSubstance(@RequestBody Substance substance) {
        return mealService.saveSubstance(substance);
    }

    @GetMapping
    public List<Substance> getSubstances() {
        return mealService.getAllAvailableSubstances();
    }
}
