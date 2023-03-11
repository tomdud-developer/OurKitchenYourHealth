package com.ourkitchen.yourhealth.mealsmicroservice.repository;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveMealRepository extends ReactiveMongoRepository<Meal, String> {
}
