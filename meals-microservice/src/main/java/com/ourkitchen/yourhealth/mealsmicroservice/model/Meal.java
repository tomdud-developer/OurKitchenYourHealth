package com.ourkitchen.yourhealth.mealsmicroservice.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;
import java.math.BigDecimal;


@Data
@Document
@Builder
public class Meal {

    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private MealType type;

    @ManyT
    private List<Igredient> igredients;


}
