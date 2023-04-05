package com.ourkitchen.yourhealth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity security) {
        security.authorizeExchange(ex -> ex
                    .pathMatchers("/eureka/**")
                    .permitAll()
                ).authorizeExchange(ex -> ex
                    .pathMatchers("/orders-microservice/api/*/orders")
                    .hasAnyAuthority("SCOPE_email")
                ).authorizeExchange(ex -> ex
                        .anyExchange()
                        .authenticated()
                )
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
                .csrf().disable();

        return security.build();
    }
}
