package com.ourkitchen.yourhealth.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class OAuth2FeignInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final ClientRegistrationRepository clientRegistrationRepository;

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String CLIENT_ID;

    private String REGISTRATION_ID = "keycloak";
    public OAuth2FeignInterceptor(ClientRegistrationRepository clientRegistrationRepository,
                                  OAuth2AuthorizedClientService authorizedClientService) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizedClientService = authorizedClientService;
        this.authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientService);
    }

    @Override
    public void apply(RequestTemplate template) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(REGISTRATION_ID);
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                REGISTRATION_ID,
                CLIENT_ID);

        if (authorizedClient == null) {
            authorizedClient =  authorizedClientManager.authorize(
                    OAuth2AuthorizeRequest.withClientRegistrationId(REGISTRATION_ID)
                            .principal(CLIENT_ID)
                            .build());

            authorizedClientService.saveAuthorizedClient(authorizedClient, new UsernamePasswordAuthenticationToken(CLIENT_ID, "", null));
        }

        template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, authorizedClient.getAccessToken().getTokenValue()));
    }
}
