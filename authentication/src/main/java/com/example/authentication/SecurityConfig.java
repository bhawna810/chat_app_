package com.example.authentication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {
	
//	@Autowired
//    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//           .cors(cors -> cors.configurationSource(request -> {
//               CorsConfiguration config = new CorsConfiguration();
//               config.setAllowedOrigins(List.of("http://localhost:3000")); // your frontend URL
//               config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//               config.setAllowedHeaders(List.of("*"));
//               config.setAllowCredentials(true);
//               return config;
//            }))
//            .csrf(csrf -> csrf.disable())  // disable CSRF for APIs
//            .authorizeHttpRequests(auth -> auth
//            	.requestMatchers("/auth/login", "/auth/signUp", "/auth/refresh").permitAll() 
//            	 .anyRequest().denyAll()
////                .anyRequest().authenticated() // all endpoints require auth
//            )
////            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
    	  http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/auth/**").permitAll()
              .anyRequest().denyAll()
          );
          return http.build();
    }
}
