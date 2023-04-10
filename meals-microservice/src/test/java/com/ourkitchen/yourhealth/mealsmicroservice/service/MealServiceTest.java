package com.ourkitchen.yourhealth.mealsmicroservice.service;

import com.ourkitchen.yourhealth.mealsmicroservice.MealsMicroserviceApplication;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Ingredient;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import com.ourkitchen.yourhealth.mealsmicroservice.model.MealType;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Substance;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.IngredientRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.MealRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.ReactiveMealRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.SubstanceRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = MealsMicroserviceApplication.class
)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Testcontainers
public class MealServiceTest {


    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;;

    @Autowired ObjectMapper objectMapper;

    @Autowired
    private ReactiveMealRepository reactiveMealRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private SubstanceRepository substanceRepository;


    private Substance substance1;
    private Substance substance2;
    private Substance substance3;
    private Ingredient ingredient1_1;
    private Ingredient ingredient1_2;
    private Ingredient ingredient2_2;
    private Ingredient ingredient2_3;

    private Meal meal1;
    private Meal meal2;



    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        substance1 = Substance.builder()
                .id("Substance1_ID")
                .name("Flovour")
                .caloriesIn100g(new BigInteger("454"))
                .carboHydrates(new BigDecimal("40"))
                .fat(new BigDecimal("0.9"))
                .proteins(new BigDecimal("2.4"))
                .sugars(new BigDecimal("3.0"))
                .build();

        substance2 = Substance.builder()
                .id("Substance2_ID")
                .name("Sugar")
                .caloriesIn100g(new BigInteger("500"))
                .carboHydrates(new BigDecimal("100"))
                .fat(new BigDecimal("0"))
                .proteins(new BigDecimal("0"))
                .sugars(new BigDecimal("100"))
                .build();

        substance3 = Substance.builder()
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

        ingredient1_1 = Ingredient.builder()
                .id("igredient1_1_id")
                .substance(substance1)
                .weight(new BigDecimal("40.5"))
                .build();

        ingredient1_2 = Ingredient.builder()
                .id("igredient1_2_id")
                .substance(substance2)
                .weight(new BigDecimal("54.5"))
                .build();

        ingredient2_2 = Ingredient.builder()
                .id("igredient2_2_id")
                .substance(substance2)
                .weight(new BigDecimal("40.5"))
                .build();

        ingredient2_3 = Ingredient.builder()
                .id("igredient2_3_id")
                .substance(substance3)
                .weight(new BigDecimal("90.4"))
                .build();

        ingredient1_1 = ingredientRepository.save(ingredient1_1);
        ingredient1_2 = ingredientRepository.save(ingredient1_2);
        ingredient2_2 = ingredientRepository.save(ingredient2_2);
        ingredient2_3 = ingredientRepository.save(ingredient2_3);

        meal1 = Meal.builder()
                .id("meal1_id")
                .type(MealType.BREAKFAST)
                .name("FirstMeal")
                .price(new BigDecimal("32.44"))
                .ingredients(List.of(ingredient1_1, ingredient1_2))
                .build();

        meal2 = Meal.builder()
                .id("meal2_id")
                .type(MealType.DINNER)
                .name("SecondMeal")
                .price(new BigDecimal("345.44"))
                .ingredients(List.of(ingredient2_2, ingredient2_3))
                .build();

        meal1 = mealRepository.save(meal2);
        meal2 = mealRepository.save(meal2);
    }

    @AfterEach
    void tearDown() {
        mealRepository.deleteAll();
        ingredientRepository.deleteAll();;
        substanceRepository.deleteAll();
    }

    @Test
    void saveSubstance() throws Exception {
        Meal meal = Meal.builder()
                .id("meal3_id")
                .type(MealType.SUPPER)
                .name("NewMeal")
                .price(new BigDecimal("33.24"))
                .ingredients(List.of(ingredient2_2, ingredient2_3, ingredient1_1))
                .build();

        MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meal))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(meal.getId()))
                .andReturn();

        Assertions.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
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
        //given
        Meal meal3 = new Meal();
        meal3.setId("235235%@#%@#2342343446$%#$");
        List<String> mealsIds = Stream.of(meal1, meal2, meal3).map(Meal::getId).toList();

        URI uri = URI.create("/api/v1/meals/meals/is-exists");

        Flux<Boolean> booleanFlux = webTestClient.method(HttpMethod.GET)
                .uri(uri)
                .bodyValue(mealsIds)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Boolean.class)
                .getResponseBody();

        StepVerifier.create(booleanFlux)
                .assertNext(Assertions::assertTrue)
                .assertNext(Assertions::assertTrue)
                .assertNext(Assertions::assertFalse)
                .expectNextCount(0)
                .verifyComplete();

    }
}