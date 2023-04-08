package com.ourkitchen.yourhealth.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
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
    private final OAuth2AuthorizedClientService authorizedClientService;

    URI uri = URI.create("http://localhost:8080/meals-microservice/api/v1/meals/meals/is-exists");

    public Flux<Boolean> areMealsExists(List<String> meaalsIds) {
        log.info("Checking mealsIds");
        System.out.println("Checking mealsIds");
        //Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("order-microservice", "order-microservice");
        OAuth2AuthorizeRequest oAuth2AuthorizeRequest =
                OAuth2AuthorizeRequest.withClientRegistrationId("order-microservice")
                        .principal("postman-1")
                        .build();
       // var accessToken = authorizedClient.getAccessToken();
        //String tokenValue = accessToken.getTokenValue();

        Flux<Boolean> booleanFlux = webClient.method(HttpMethod.GET)
                .uri(uri)
                .bodyValue(meaalsIds)
               // .headers(header -> header.setBearerAuth(tokenValue))
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
