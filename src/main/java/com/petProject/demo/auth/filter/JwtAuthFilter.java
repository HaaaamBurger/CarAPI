package com.petProject.demo.auth.filter;

import com.petProject.demo.security.exception.TokenExpiredException;
import com.petProject.demo.service.JwtService;
import com.petProject.demo.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeaderValue = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeaderValue == null || !authorizationHeaderValue.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtValue = authorizationHeaderValue.substring(AUTHORIZATION_HEADER_PREFIX.length());

        if(jwtService.isTokenValid(jwtValue)) {
            throw new TokenExpiredException("Token expired");
        }

        String username = jwtService.extractUsername(jwtValue);

        if (!username.isBlank()) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(username, userDetails.getPassword(), userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            emptyContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(emptyContext);
        }

        filterChain.doFilter(request, response);
    }
}
