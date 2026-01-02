package com.example.peer_to_peer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterchain( HttpSecurity http) throws Exception {
		
		System.out.println(" inside peer to peer ");
		
		http
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth.
				
				requestMatchers("/peer/**").permitAll()
	              .anyRequest().denyAll()
	     );
		return http.build();
		
	}
}
