package com.petProject.demo.service;

import com.petProject.demo.common.type.Roles;
import com.petProject.demo.common.util.AuthUtil;
import com.petProject.demo.dto.AuthRequestDto;
import com.petProject.demo.dto.AuthResponseDto;
import com.petProject.demo.dto.TokenDto;
import com.petProject.demo.security.exception.UnexpectedUserRoleException;
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

    private final AuthUtil authUtil;

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
        return TokenDto
                .builder()
                .build();
    }
}
