package com.petProject.demo.common.util;

import com.petProject.demo.common.type.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    private final PasswordEncoder passwordEncoder;

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean isMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String substringToken(String token) {
        return token.substring(AUTHORIZATION_HEADER_PREFIX.length());
    }

    public boolean isValidRole(String role) {
        return role.equals(String.valueOf(Roles.BUYER)) || role.equals(String.valueOf(Roles.SELLER));
    }

}
