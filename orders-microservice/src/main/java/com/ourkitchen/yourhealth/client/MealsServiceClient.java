package com.ourkitchen.yourhealth.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MealsServiceClient {

    private final WebClient webClient;

    URI uri = URI.create("http://localhost:8080/meals-microservice/api/v1/meals/meals/is-exists");

    public Flux<Boolean> areMealsExists(List<String> meaalsIds) {
        log.info("Checking mealsIds");
        System.out.println("Checking mealsIds");
        Flux<Boolean> booleanFlux = webClient.method(HttpMethod.GET)
                .uri(uri)
                .bodyValue(meaalsIds)
                .retrieve()
/*                .onStatus(HttpStatus::is4xxClientError, (clientResponse -> {
                    log.info("Status code : {}", clientResponse.statusCode().value());
                    throw new RuntimeException("error");
                }))
                .onStatus(HttpStatus::is5xxServerError, (clientResponse -> {
                    log.info("Status code : {}", clientResponse.statusCode().value());
                    throw new RuntimeException("error");
                }))
                .onStatus(HttpStatus::is2xxSuccessful, (clientResponse -> {
                    log.info("Status code : {}", clientResponse.statusCode().value());
                    throw new RuntimeException("good");
                }))*/
                .bodyToFlux(Boolean.class);
        booleanFlux.subscribe(x -> log.info("{}", x));

        booleanFlux.doOnEach(booleanSignal -> System.out.println(booleanSignal.get()));
        return booleanFlux.log();
    }

}
