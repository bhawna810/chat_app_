package com.example.authentication.services;

//public class JwtService {
//
//}

//package com.example.auth_service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    
    @Value("${security.jwt.refreshsecret-key}")
    private String refreshsecretKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    public String extractUsernameRefresh(String token) {
        return extractClaimRefresh(token, Claims::getSubject);
    }

    public <T> T extractClaimRefresh(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaimsRefresh(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }
    
    public String generateResfreshToken(UserDetails userDetails) {
        return generateResfreshToken(new HashMap<>(), userDetails);
    }

    public String generateResfreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildResfreshToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    private String buildResfreshToken(
    		Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                .signWith(getSignInKeyRefresh(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public boolean isTokenValidRefresh(String token, UserDetails userDetails) {
        final String username = extractUsernameRefresh(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpiredRefresh(token);
    }

    private boolean isTokenExpiredRefresh(String token) {
        return extractExpirationRefresh(token).before(new Date());
    }
    
    private Date extractExpirationRefresh(String token) {
        return extractClaimRefresh(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    private Claims extractAllClaimsRefresh(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKeyRefresh())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKeyRefresh() {
        byte[] keyBytes = Decoders.BASE64.decode(refreshsecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}