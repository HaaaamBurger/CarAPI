package com.petProject.demo.common.util;

import com.petProject.demo.common.type.ERoles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {
    private final PasswordEncoder passwordEncoder;

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean isMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public boolean isValidRole(String role) {
        return role.equals(String.valueOf(ERoles.BUYER)) || role.equals(String.valueOf(ERoles.SELLER));
    }
}
