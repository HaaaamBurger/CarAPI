package com.petProject.demo.service;

import com.petProject.demo.common.util.AuthUtil;
import com.petProject.demo.common.util.UserUtil;
import com.petProject.demo.dto.AuthRequestDto;
import com.petProject.demo.dto.TokenDto;
import com.petProject.demo.security.exception.UnexpectedUserRoleException;
import com.petProject.demo.security.exception.WrongCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petProject.demo.common.mapper.UserMapper;
import com.petProject.demo.dto.UserDto;
import com.petProject.demo.model.User;
import com.petProject.demo.repository.UserRepository;
import com.petProject.demo.security.exception.UserAlreadyExistsException;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.CredentialException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthUtil authUtil;
    private final UserUtil userUtil;

    @Value("${jwt.access-token.ttl-millis}")
    private Long accessTokenMillis;

    @Value("${jwt.refresh-token.ttl-millis}")
    private Long refreshTokenMillis;

    @Transactional
    public UserDto register(UserDto userDto) {
        userRepository.findUserByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException("User already exists.");

        });

        if (!authUtil.isValidRole(userDto.getRole())) {
            throw new UnexpectedUserRoleException("Unappropriate role.");
        }

        String hashedPassword = authUtil.hashPassword(userDto.getPassword());
        userDto.setPassword(hashedPassword);

        User savedUser = userRepository.save(userMapper.fromDto(userDto));

        return userMapper.toDto(savedUser);
    }


    @Transactional
    public TokenDto login(AuthRequestDto authRequestDto) {
        return userRepository.findUserByEmail(authRequestDto.getEmail()).map(user -> {
            if (!authUtil.isMatch(authRequestDto.getPassword(), user.getPassword())) {
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
}
