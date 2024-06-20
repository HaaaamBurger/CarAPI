package com.petProject.demo.service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String tokenSecret;

    private Key key;

    private JwtParser jwtParser;

    @PostConstruct
    public void setUpKey() {
        key = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String generateToken(UserDetails userDetails, long tokenTimeMillis) {
        List<String> userRoles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .addClaims(Map.of("roles", userRoles))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenTimeMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
