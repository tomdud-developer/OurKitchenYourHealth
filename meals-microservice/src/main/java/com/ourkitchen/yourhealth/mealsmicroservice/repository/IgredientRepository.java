package com.ourkitchen.yourhealth.mealsmicroservice.repository;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Igredient;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IgredientRepository extends MongoRepository<Igredient, String> {
}
