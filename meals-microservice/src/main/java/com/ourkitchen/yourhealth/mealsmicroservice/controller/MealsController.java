package com.ourkitchen.yourhealth.mealsmicroservice.controller;


import com.ourkitchen.yourhealth.mealsmicroservice.model.Igredient;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Substance;
import com.ourkitchen.yourhealth.mealsmicroservice.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("api/v1/meals")
@RequiredArgsConstructor
public class MealsController {

    private final MealService mealService;

    @PostMapping("substances")
    public Substance saveSubstance(@RequestBody Substance substance) {
        return mealService.saveSubstance(substance);
    }

    @GetMapping("substances")
    public List<Substance> getSubstances() {
        return mealService.getAllAvailableSubstances();
    }
    @PostMapping("igredients")
    public Igredient saveIgredient(@RequestBody Igredient igredient) {
        return mealService.saveIgredient(igredient);
    }

    @PostMapping("meals")
    public Meal saveMeal(@RequestBody Meal meal) {
        return mealService.saveMeal(meal);
    }

    @DeleteMapping("meals/{mealId}")
    public void deleteMeal(@PathVariable String mealId) {
        mealService.deleteMeal(mealId);
    }

    @GetMapping("meals")
    public Flux<Meal> getMeals() {
        return mealService.getAvailableMeals();
    }

    @GetMapping("meals/is-exists")
    public Flux<Boolean> isMealsAvailable(@RequestBody List<String> mealsIds){
        return mealService.isMealsAvailable(mealsIds);
    }




}
