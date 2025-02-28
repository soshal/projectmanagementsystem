package com.soshal.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public String generateToken(Authentication authentication) {
       // SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWTAUTH.SECRET_KEY));
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWTAUTH.SECRET_KEY));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    public String extractEmail(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWTAUTH.SECRET_KEY));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // Assuming subject stores the email
    }
}

