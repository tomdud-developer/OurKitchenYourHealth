package com.ourkitchen.yourhealth.mealsmicroservice.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;

@Data
@Document
@Builder
public class Igredient {
    @Id
    private String id;
    private BigDecimal weight;

    @DocumentReference(lazy=false)// lookup = "{ 'substanceId' : ?#{#target} }")
    private Substance substance;
}
