package com.ourkitchen.yourhealth.mealsmicroservice.repository;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Substance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubstanceRepository extends MongoRepository<Substance, String> {
}
