package com.ourkitchen.yourhealth.mealsmicroservice.config;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

@Component
@Profile("test")
@PropertySource(value = "classpath:application-test.yaml")
public class ConfigTestProfile {

}
