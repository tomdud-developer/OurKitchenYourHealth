package com.ourkitchen.yourhealth.mealsmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    private String id;
    private BigDecimal weight;

    @DocumentReference(lazy=true)// lookup = "{ 'substanceId' : ?#{#target} }")
    private Substance substance;
}
