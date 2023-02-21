package com.ourkitchen.yourhealth.mealsmicroservice;

import com.ourkitchen.yourhealth.mealsmicroservice.model.Igredient;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Meal;
import com.ourkitchen.yourhealth.mealsmicroservice.model.MealType;
import com.ourkitchen.yourhealth.mealsmicroservice.model.Substance;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.IgredientRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.MealRepository;
import com.ourkitchen.yourhealth.mealsmicroservice.repository.SubstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class MealsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealsMicroserviceApplication.class, args);
	}


	@Autowired
	@Bean
	@Transactional
	public String mealTest(MealRepository mealRepository, IgredientRepository igredientRepository, SubstanceRepository substanceRepository) {
		Substance substance1 = Substance.builder()
				.name("yoghurt")
				.fat(new BigDecimal("0.4"))
				.carboHydrates(new BigDecimal("3.6"))
				.sugars(new BigDecimal("3.2"))
				.proteins(new BigDecimal("10"))
				.caloriesIn100g(new BigInteger("58"))
				.build();

		Substance substance2 = Substance.builder()
				.name("cereals")
				.fat(new BigDecimal("2"))
				.carboHydrates(new BigDecimal("50"))
				.sugars(new BigDecimal("5.2"))
				.proteins(new BigDecimal("7"))
				.caloriesIn100g(new BigInteger("400"))
				.build();

		substance1 = substanceRepository.save(substance1);
		substance2 = substanceRepository.save(substance2);



		Igredient igredient1 = Igredient.builder()
				.weight(new BigDecimal("200"))
				.substance(substance1)
				.build();

		Igredient igredient2 = Igredient.builder()
				.weight(new BigDecimal("50"))
				.substance(substance2)
				.build();

		igredient1 = igredientRepository.save(igredient1);
		igredient2 = igredientRepository.save(igredient2);


		Meal meal = Meal.builder()
				.name("Cereals with yoghurt")
				.price(new BigDecimal("8.0"))
				.type(MealType.BREAKFAST)
				.igredients(Arrays.asList(igredient1, igredient2))
				.build();

		mealRepository.save(meal);

		substanceRepository.findById("63f4acc8f562b827b2b5c86b").get().setProteins(new BigDecimal("999999.999"));

		return "ok";
	}



	/*
			Na
		100 g
		Wartość energetyczna (kcal) 58
		Tłuszcz 0,4 g
		Kwasy tłuszczowe nasycone 0,1 g
		Kwasy tłuszczowe trans 0 g
		Cholesterol 5 mg
		Sód 36 mg
		Potas 141 mg
		Węglowodany 3,6 g
		Błonnik 0 g
		Cukry 3,2 g
		Białko 10 g
	 */
}
