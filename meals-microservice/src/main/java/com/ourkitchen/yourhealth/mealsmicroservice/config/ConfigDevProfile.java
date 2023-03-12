package com.ourkitchen.yourhealth.mealsmicroservice.config;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@PropertySource(value = "classpath:application.yaml")
public class ConfigDevProfile {

}
