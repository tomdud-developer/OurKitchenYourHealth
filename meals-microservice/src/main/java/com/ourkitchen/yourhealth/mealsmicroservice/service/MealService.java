package com.ourkitchen.yourhealth.mealsmicroservice.service;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Ingredient;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Substance;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.IngredientRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.MealRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.ReactiveMealRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.SubstanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MealService {

    private final IngredientRepository ingredientRepository;
    private final MealRepository mealRepository;

    private final ReactiveMealRepository reactiveMealRepository;
    private final SubstanceRepository substanceRepository;

    @Transactional
    public Substance saveSubstance(Substance substance) {
       /* Query query = new Query().addCriteria(Criteria.where("id").is(substance.getId()));
        Update update = new Update().set("name", substance.getName());
        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions().returnNew(true).upsert(true);

        return mongoTemplate.findAndModify(query, update, findAndModifyOptions, Substance.class);

        */
        return substanceRepository.save(substance);
    }

    @Transactional
    public Ingredient saveIgredient(Ingredient ingredient) {
        String substanceId = ingredient.getSubstance().getId();

        if(substanceId == null) throw new RuntimeException("You are not provide substance id");
        if(ingredient.getWeight() == null) throw new RuntimeException("You are not provide weight");

        Optional<Substance> optionalSubstance = substanceRepository.findById(substanceId);
        optionalSubstance.ifPresentOrElse(ingredient::setSubstance,
                () -> {throw new RuntimeException("You are not provide correct ingredient id");}
        );

        return ingredientRepository.save(ingredient);
    }

    @Transactional
    public List<Substance> getAllAvailableSubstances() {
        return substanceRepository.findAll();
    }

    @Transactional
    public Meal saveMeal(Meal meal) {
        List<Ingredient> ingredients = new ArrayList<>();
        meal.getIngredients().forEach((igredient) -> {
            Substance substance = igredient.getSubstance();
            if(substance == null) throw new RuntimeException("You didn't provide substance");
            ingredients.add(this.saveIgredient(igredient));
        });

        meal.setIngredients(ingredients);

        return mealRepository.save(meal);
    }

    @Transactional
    public Flux<Meal> getAvailableMeals() {
        return reactiveMealRepository.findAll();
    }

    @Transactional
    public void deleteMeal(String mealId) {
        Optional<Meal> optionalMeal = mealRepository.findById(mealId);
        optionalMeal.ifPresentOrElse((meal) -> {
            meal.getIngredients()
                    .forEach(igredient -> ingredientRepository.deleteById(
                            igredient.getId()
                    ));
        },() -> {
            throw new RuntimeException("Didn't find meal");
        });

        mealRepository.deleteById(mealId);
    }

    public Flux<Boolean> isMealsAvailable(List<String> mealsIds) {

        log.info("Checkings meals ids");

        return Flux.fromIterable(mealsIds).map(mealId -> {
            Meal meal = reactiveMealRepository.findById(mealId).block();
            return meal != null;
        });
    }

    public Flux<Mono<Meal>> getMealsByIds(List<String> mealsIds) {
        return Flux.fromIterable(mealsIds).map(reactiveMealRepository::findById);
    }

    public Mono<BigDecimal> calculateOrderPrice(List<String> mealsIds) {
        Flux<Meal> mealFlux = Flux.fromIterable(mealsIds).flatMap(reactiveMealRepository::findById);
        Flux<BigDecimal> bigDecimalFlux = mealFlux.map(Meal::getPrice);
        Mono<BigDecimal> price = bigDecimalFlux.reduce(BigDecimal::add);

        return price;
    }
}
