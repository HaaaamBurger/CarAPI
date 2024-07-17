package com.petProject.demo.service;

import com.petProject.demo.common.type.AccountTypes;
import com.petProject.demo.common.type.Roles;
import com.petProject.demo.dto.AuthRequestDto;
import com.petProject.demo.dto.TokenDto;
import com.petProject.demo.security.exception.TokenExpiredException;
import com.petProject.demo.security.exception.UnexpectedUserRoleException;
import com.petProject.demo.security.exception.WrongCredentialsException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petProject.demo.common.mapper.UserMapper;
import com.petProject.demo.dto.UserDto;
import com.petProject.demo.model.User;
import com.petProject.demo.repository.UserRepository;
import com.petProject.demo.security.exception.EntityAlreadyExistsException;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    @Value("${jwt.access-token.ttl-millis}")
    private Long accessTokenMillis;

    @Value("${jwt.refresh-token.ttl-millis}")
    private Long refreshTokenMillis;

    @Transactional
    public UserDto register(UserDto userDto) {
        userRepository.findUserByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new EntityAlreadyExistsException("User already exists.");

        });

        if (!userDto.getRole().equals(String.valueOf(Roles.valueOf(userDto.getRole())))) {
            throw new UnexpectedUserRoleException("Unappropriated role.");
        }

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(hashedPassword);
        userDto.setCars(new ArrayList<>());
        userDto.setType(AccountTypes.BASE);

        User savedUser = userRepository.save(userMapper.fromDto(userDto));

        return userMapper.toDto(savedUser);
    }


    @Transactional
    public TokenDto login(AuthRequestDto authRequestDto) {
        return userRepository.findUserByEmail(authRequestDto.getEmail()).map(user -> {
            if (!passwordEncoder.matches(authRequestDto.getPassword(), user.getPassword())) {
                throw new WrongCredentialsException("Wrong user credentials.");
            }

            String accessToken = jwtService.generateToken(user, accessTokenMillis);
            String refreshToken = jwtService.generateToken(user, refreshTokenMillis);

            return TokenDto
                    .builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        }).orElseThrow(() -> new WrongCredentialsException("Wrong user credentials."));
    }

    public TokenDto refresh(String token) {

        String convertedToken = token.substring(AUTHORIZATION_HEADER_PREFIX.length());

        if (jwtService.isTokenValid(convertedToken)) {
            throw new TokenExpiredException("Token expired");
        }

        Claims tokenClaims = jwtService.extractAllClaims(convertedToken);
        String username = jwtService.extractUsername(convertedToken);
        String accessToken = jwtService.generateTokenByRefresh(tokenClaims, username, accessTokenMillis);
        String refreshToken = jwtService.generateTokenByRefresh(tokenClaims, username, refreshTokenMillis);

        return TokenDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }
}
