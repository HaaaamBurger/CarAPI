package com.petProject.demo.service;

import com.petProject.demo.common.util.AuthUtil;
import com.petProject.demo.dto.AuthRequestDto;
import com.petProject.demo.dto.TokenDto;
import com.petProject.demo.security.exception.UnexpectedUserRoleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petProject.demo.common.mapper.UserMapper;
import com.petProject.demo.dto.UserDto;
import com.petProject.demo.model.User;
import com.petProject.demo.repository.UserRepository;
import com.petProject.demo.security.exception.UserAlreadyExistsException;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthUtil authUtil;

    @Value("${jwt.access-token.ttl-millis}")
    private String accessTokenMillis;

    @Value("${jwt.refresh-token.ttl-millis}")
    private String refreshTokenMillis;

    @Transactional
    public UserDto register(UserDto userDto) {
        userRepository.findUserByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException("User already exists!!");
        });

        if (!authUtil.isValidRole(userDto.getRole())) {
            throw new UnexpectedUserRoleException("Unappropriate role.");
        }

        String hashedPassword = authUtil.hashPassword(userDto.getPassword());
        userDto.setPassword(hashedPassword);

        User savedUser = userRepository.save(userMapper.fromDto(userDto));

        return userMapper.toDto(savedUser);
    }

    public TokenDto login(AuthRequestDto authRequestDto) {
        User userDetails =

        String accessToken = jwtService.generateToken();

        String refreshToken;

        return TokenDto
                .builder()
                .build();
    }
}
