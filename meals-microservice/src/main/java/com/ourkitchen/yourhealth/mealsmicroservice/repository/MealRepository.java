package com.ourkitchen.yourhealth.mealsmicroservice.repository;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {
}
