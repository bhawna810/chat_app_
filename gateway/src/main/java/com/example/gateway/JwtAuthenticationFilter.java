package com.example.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.example.gateway.services.JwtService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

//    private final JwtService jwtService;
//
//    public JwtAuthenticationFilter(JwtService jwtService) {
//        this.jwtService = jwtService;
//    }
	
	@Autowired
	private JwtService jwtService;


//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//
//        String path = exchange.getRequest().getPath().toString();
//
//        // Public endpoints
//        if (path.startsWith("/auth/login") ||
//            path.startsWith("/auth/refresh") ||
//            path.startsWith("/auth/signUp")) {
//            return chain.filter(exchange);
//        }
//
//        String authHeader = exchange.getRequest()
//                                    .getHeaders()
//                                    .getFirst("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        String token = authHeader.substring(7);
//        
//        System.out.println(" token " + token);
//
//        try {
//            if (!jwtService.isTokenValid(token)) {
//                exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
//                return exchange.getResponse().setComplete();
//            }
//        } catch (Exception e) {
//            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        System.out.println(" token " + token);
//        
//        
//        
//        return chain.filter(exchange);
//    }
	
	

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

	    String path = exchange.getRequest().getPath().toString();

	    if (path.startsWith("/auth/login") ||
	        path.startsWith("/auth/refresh") ||
	        path.startsWith("/auth/signUp")) {
	        return chain.filter(exchange);
	    }

	    String authHeader = exchange.getRequest()
	            .getHeaders()
	            .getFirst("Authorization");

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
	        return exchange.getResponse().setComplete();
	    }

	    String token = authHeader.substring(7);

	    try {
	        if (!jwtService.isTokenValid(token)) {
	            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
	            return exchange.getResponse().setComplete();
	        }

	        // ✅ MARK USER AS AUTHENTICATED
	        UsernamePasswordAuthenticationToken authentication =
	                new UsernamePasswordAuthenticationToken(
	                        "user", // or extract username from token
	                        null,
	                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
	                );

	        return chain.filter(exchange)
	                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));

	    } catch (Exception e) {
	        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
	        return exchange.getResponse().setComplete();
	    }
	}

}
