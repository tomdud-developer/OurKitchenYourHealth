package com.ourkitchen.yourhealth.mealsmicroservice.controller;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Igredient;
import com.ourkitchen.yourhealth.mealsmicroservice.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/igredients")
@RequiredArgsConstructor
public class IgredientsController {

    private final MealService mealService;

    @PostMapping
    public Igredient saveIgredient(@RequestBody Igredient igredient) {
        return mealService.saveIgredient(igredient);
    }
}
