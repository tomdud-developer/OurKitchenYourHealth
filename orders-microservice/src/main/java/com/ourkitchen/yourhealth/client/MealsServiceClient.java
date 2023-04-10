package com.ourkitchen.yourhealth.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MealsServiceClient {

    private final WebClient webClient;

    @Value("${meal.client.uris.is-exists}")
    private URI uriIsExists;

    @Value("${meal.client.uris.calculate-order-price}")
    private URI uriCalculateOrderPrice;

    public Flux<Boolean> areMealsExists(List<String> mealsIds) {
        log.info("Checking mealsIds");

        Flux<Boolean> booleanFlux = webClient.method(HttpMethod.GET)
                .uri(uriIsExists)
                .bodyValue(mealsIds)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    log.info("Status code : {}", clientResponse.statusCode().value());
                    return Mono.error(new RuntimeException("Client error"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.info("Status code: {}", response.statusCode().value());
                    return Mono.error(new RuntimeException("Server error"));
                })
                .bodyToFlux(Boolean.class);
        booleanFlux.subscribe(x -> log.info("{}", x));
        return booleanFlux.log();
    }

    public Mono<BigDecimal> calculateOrderPrice(List<String> mealsIds) {
        log.info("Calculate order price");

        Mono<BigDecimal> booleanFlux = webClient.method(HttpMethod.GET)
                .uri(uriCalculateOrderPrice)
                .bodyValue(mealsIds)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    log.info("Status code : {}", clientResponse.statusCode().value());
                    return Mono.error(new RuntimeException("Client error"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.info("Status code: {}", response.statusCode().value());
                    return Mono.error(new RuntimeException("Server error"));
                })
                .bodyToMono(BigDecimal.class);

        return booleanFlux.log();
    }

}
