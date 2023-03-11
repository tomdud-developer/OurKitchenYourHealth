package com.ourkitchen.yourhealth.mealsmicroservice.service;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Igredient;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import com.ourkitchen.yourhealth.mealsmicroservice.model.MealType;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Substance;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.IgredientRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.MealRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.ReactiveMealRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.SubstanceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@AutoConfigureWebTestClient
class MealServiceTest {

/*    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ReactiveMealRepository reactiveMealRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private IgredientRepository igredientRepository;

    @Autowired
    private SubstanceRepository substanceRepository;


    @BeforeEach
    void setUp() {
        Substance substance1 = Substance.builder()
                .id("Substance1_ID")
                .name("Flovour")
                .caloriesIn100g(new BigInteger("454.4"))
                .carboHydrates(new BigDecimal("40"))
                .fat(new BigDecimal("0.9"))
                .proteins(new BigDecimal("2.4"))
                .sugars(new BigDecimal("3.0"))
                .build();

        Substance substance2 = Substance.builder()
                .id("Substance2_ID")
                .name("Sugar")
                .caloriesIn100g(new BigInteger("500.1"))
                .carboHydrates(new BigDecimal("100"))
                .fat(new BigDecimal("0"))
                .proteins(new BigDecimal("0"))
                .sugars(new BigDecimal("100"))
                .build();

        Substance substance3 = Substance.builder()
                .id("Substance3_ID")
                .name("Milk")
                .caloriesIn100g(new BigInteger("60"))
                .carboHydrates(new BigDecimal("9"))
                .fat(new BigDecimal("3.2"))
                .proteins(new BigDecimal("7"))
                .sugars(new BigDecimal("2"))
                .build();

        substance1 = substanceRepository.save(substance1);
        substance2 = substanceRepository.save(substance2);
        substance3 = substanceRepository.save(substance3);

        Igredient igredient1_1 = Igredient.builder()
                .id("igredient1_1_id")
                .substance(substance1)
                .weight(new BigDecimal("40.5"))
                .build();

        Igredient igredient1_2 = Igredient.builder()
                .id("igredient1_2_id")
                .substance(substance2)
                .weight(new BigDecimal("54.5"))
                .build();

        Igredient igredient2_2 = Igredient.builder()
                .id("igredient2_2_id")
                .substance(substance2)
                .weight(new BigDecimal("40.5"))
                .build();

        Igredient igredient2_3 = Igredient.builder()
                .id("igredient2_3_id")
                .substance(substance3)
                .weight(new BigDecimal("90.4"))
                .build();

        igredient1_1 = igredientRepository.save(igredient1_1);
        igredient1_2 = igredientRepository.save(igredient1_2);
        igredient2_2 = igredientRepository.save(igredient2_2);
        igredient2_3 = igredientRepository.save(igredient2_3);

        Meal meal1 = Meal.builder()
                .id("meal1_id")
                .type(MealType.BREAKFAST)
                .name("FirstMeal")
                .price(new BigDecimal("32.44"))
                .igredients(List.of(igredient1_1, igredient1_2))
                .build();

        Meal meal2 = Meal.builder()
                .id("meal2_id")
                .type(MealType.DINNER)
                .name("SecondMeal")
                .price(new BigDecimal("345.44"))
                .igredients(List.of(igredient2_2, igredient2_3))
                .build();

        meal1 = mealRepository.save(meal2);
        meal2 = mealRepository.save(meal2);
    }
*/
    @AfterEach
    void tearDown() {
    }

    @Test
    void saveSubstance() {
    }

    @Test
    void saveIgredient() {
    }

    @Test
    void getAllAvailableSubstances() {
    }

    @Test
    void saveMeal() {
    }

    @Test
    void getAvailableMeals() {
    }

    @Test
    void deleteMeal() {
    }

    @Test
    void isMealsAvailable() {
    }
}