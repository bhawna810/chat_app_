package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        return http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(auth -> auth
                .pathMatchers("/auth/login", "/auth/refresh", "/auth/signUp").permitAll()
                .anyExchange().authenticated()
            )
            //  THIS LINE connects security → JWT filter
            .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
    }
}

