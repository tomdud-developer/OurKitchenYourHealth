package com.ourkitchen.yourhealth.mealsmicroservice.service;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Igredient;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Substance;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.IgredientRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.MealRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.SubstanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealService {

    private final IgredientRepository igredientRepository;
    private final MealRepository mealRepository;
    private final SubstanceRepository substanceRepository;
    private final MongoTemplate mongoTemplate;

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
    public Igredient saveIgredient(Igredient igredient) {
        String substanceId = igredient.getSubstance().getId();

        if(substanceId == null) throw new RuntimeException("You are not provide substance id");
        if(igredient.getWeight() == null) throw new RuntimeException("You are not provide weight");


        Optional<Substance> optionalSubstance = substanceRepository.findById(substanceId);
        optionalSubstance.ifPresentOrElse(igredient::setSubstance,
                () -> {throw new RuntimeException("You are not provide correct id");}
        );

        return igredientRepository.save(igredient);
    }

    @Transactional
    public List<Substance> getAllAvailableSubstances() {
        return substanceRepository.findAll();
    }

    @Transactional
    public Meal saveMeal(Meal meal) {
        List<Igredient> igredients = new ArrayList<>();
        meal.getIgredients().forEach((igredient) -> {
            Substance substance = igredient.getSubstance();
            if(substance == null) throw new RuntimeException("You didn't provie substance");

            igredients.add(this.saveIgredient(igredient));
        });

        meal.setIgredients(igredients);

        return mealRepository.save(meal);
    }

    @Transactional
    public List<Meal> getAvailableMeals() {
        return mealRepository.findAll();
    }

    @Transactional
    public void deleteMeal(String mealId) {
        Optional<Meal> optionalMeal = mealRepository.findById(mealId);
        optionalMeal.ifPresentOrElse((meal) -> {
            meal.getIgredients()
                    .forEach(igredient -> igredientRepository.deleteById(
                            igredient.getId()
                    ));
        },() -> {
            throw new RuntimeException("Didn't find meal");
        });

        mealRepository.deleteById(mealId);
    }
}
