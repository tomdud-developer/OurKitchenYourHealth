package com.ourkitchen.yourhealth.mealsmicroservice.repository;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, String> {
}
