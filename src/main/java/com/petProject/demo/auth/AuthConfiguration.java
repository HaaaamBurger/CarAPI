package com.petProject.demo.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    // @SneakyThrows
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
    //     return httpSecurity
    //         .csrf(CsrfConfigurer::disable)
    //         .cors(CorsConfigurer::disable)
    //         .authorizeHttpRequests(request -> {
    //             request
    //                 .requestMatchers("/users").permitAll()
    //                 .anyRequest().authenticated()

    //         })
    //         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //         .build()
    // }
    
}
