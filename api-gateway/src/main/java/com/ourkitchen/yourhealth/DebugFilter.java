package com.ourkitchen.yourhealth;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebInitParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.annotation.Annotation;

public class DebugFilter implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(DebugFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        var authentication = ReactiveSecurityContextHolder.getContext().map(securityContext -> {
                    logger.info(securityContext.getAuthentication().getPrincipal().toString());
                    return securityContext.getAuthentication();
                }
        );
       /* return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication != null && authentication.getPrincipal() instanceof UserDetails)
                .map(authentication -> (UserDetails) authentication.getPrincipal())
                .map(UserDetails::getUsername)
                .flatMap(username -> {
                    // do something with the username...

                    return chain.filter(exchange);
                });*/

        return null;
    }
}
