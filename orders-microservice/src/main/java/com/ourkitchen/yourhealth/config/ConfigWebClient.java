package com.ourkitchen.yourhealth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConfigWebClient {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
