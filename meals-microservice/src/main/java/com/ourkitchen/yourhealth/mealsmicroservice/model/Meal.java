package com.ourkitchen.yourhealth.mealsmicroservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


import java.util.List;
import java.math.BigDecimal;


@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meal {

    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private MealType type;

    @DocumentReference(lazy=true) //, lookup = "{ 'igredientsIds' : ?#{#target} }")
    private List<Igredient> igredients;


}
