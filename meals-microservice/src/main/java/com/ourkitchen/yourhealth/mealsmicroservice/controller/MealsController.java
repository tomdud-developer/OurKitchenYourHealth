package com.ourkitchen.yourhealth.mealsmicroservice.controller;


import com.ourkitchen.yourhealth.mealsmicroservice.model.Igredient;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Substance;
import com.ourkitchen.yourhealth.mealsmicroservice.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/meals-microservice")
@RequiredArgsConstructor
public class MealsController {

    private final MealService mealService;

    @PostMapping("substance")
    public Substance saveSubstance(@RequestBody Substance substance) {
        return mealService.saveSubstance(substance);
    }

    @GetMapping("substance")
    public List<Substance> getSubstances() {
        return mealService.getAllAvailableSubstances();
    }
    @PostMapping("igredient")
    public Igredient saveIgredient(@RequestBody Igredient igredient) {
        return mealService.saveIgredient(igredient);
    }

    @PostMapping("meal")
    public Meal saveMeal(@RequestBody Meal meal) {
        return mealService.saveMeal(meal);
    }

    @DeleteMapping("meal/{mealId}")
    public void deleteMeal(@PathVariable String mealId) {
        mealService.deleteMeal(mealId);
    }

    @GetMapping("meal")
    public List<Meal> getMeals() {
        return mealService.getAvailableMeals();
    }
}
