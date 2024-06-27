package com.petProject.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.petProject.demo.dto.TokenDto;
import io.jsonwebtoken.*;
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
import java.util.function.Function;

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

    public List<String> extractUserRoles(UserDetails userDetails) {
        return userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public String generateToken(UserDetails userDetails, long tokenTimeMillis) {
        List<String> userRoles = extractUserRoles(userDetails);

        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .addClaims(Map.of("roles", userRoles))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenTimeMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateTokenByRefresh(Claims claims, String username, long tokenTimeMillis) {
        return Jwts
                .builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenTimeMillis))
                .signWith(key ,SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractFromToken(String token, Function<Claims, T> extractor) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return extractor.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractFromToken(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expiresAt = decodedJWT.getExpiresAt();

        return expiresAt.before(new Date());
    }
}
