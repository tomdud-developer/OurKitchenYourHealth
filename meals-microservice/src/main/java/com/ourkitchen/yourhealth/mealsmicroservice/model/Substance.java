package com.ourkitchen.yourhealth.mealsmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Substance {
    @Id
    private String id;
    private String name;
    private BigDecimal proteins;
    private BigDecimal fat;
    private BigDecimal carboHydrates;
    private BigDecimal sugars;
    private BigInteger caloriesIn100g;
}
