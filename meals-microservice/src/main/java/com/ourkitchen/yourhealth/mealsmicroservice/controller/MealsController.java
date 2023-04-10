package com.ourkitchen.yourhealth.mealsmicroservice.controller;


import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import com.ourkitchen.yourhealth.mealsmicroservice.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/meals")
@RequiredArgsConstructor
public class MealsController {

    private final MealService mealService;


    @PostMapping()
    public ResponseEntity<Meal> saveMeal(@RequestBody Meal meal) {
        return new ResponseEntity<>(mealService.saveMeal(meal), HttpStatus.CREATED);
    }

    @DeleteMapping("{mealId}")
    public void deleteMeal(@PathVariable String mealId) {
        mealService.deleteMeal(mealId);
    }

    @GetMapping()
    public Flux<Meal> getMeals() {
        return mealService.getAvailableMeals();
    }

    @GetMapping("/is-exists")
    public Flux<Boolean> isMealsAvailable(@RequestBody List<String> mealsIds){
        return mealService.isMealsAvailable(mealsIds);
    }

    @GetMapping("/by-ids")
    public Flux<Mono<Meal>> getMealsByIds(@RequestBody List<String> mealsIds){
        return mealService.getMealsByIds(mealsIds);
    }

    @GetMapping("/calculate-order-price")
    public Mono<BigDecimal> calculateOrderPrice(@RequestBody List<String> mealsIds){
        return mealService.calculateOrderPrice(mealsIds);
    }




}
