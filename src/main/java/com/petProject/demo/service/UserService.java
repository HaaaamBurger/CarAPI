package com.petProject.demo.service;

import java.util.List;

import com.petProject.demo.common.type.Roles;
import com.petProject.demo.common.type.UserSchema;
import com.petProject.demo.security.exception.EntityAlreadyExistsException;
import com.petProject.demo.security.exception.NotFoundException;
import com.petProject.demo.security.exception.UnexpectedUserRoleException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petProject.demo.common.mapper.UserMapper;
import com.petProject.demo.dto.UserDto;
import com.petProject.demo.model.User;
import com.petProject.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService, UserSchema {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        userRepository.findUserByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new EntityAlreadyExistsException("User already exists.");
        });

        if (!userDto.getRole().equals(String.valueOf(Roles.BUYER)) || userDto.getRole().equals(String.valueOf(Roles.SELLER))) {
            throw new UnexpectedUserRoleException("Unappropriated role.");
        }

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(hashedPassword);
        userDto.setType((userDto.getType()));

        User savedUser = userRepository.save(userMapper.fromDto(userDto));

        return userMapper.toDto(savedUser);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto getByCarId(String carId) {
        User storedUser = checkUserForExistenceAndThrowNotFound(carId);
        return userMapper.toDto(storedUser);
    }

    @Override
    public UserDto removeByCarId(String carId) {
        User storedUser = checkUserForExistenceAndThrowNotFound(carId);
        userRepository.delete(storedUser);

        return userMapper.toDto(storedUser);
    }

    @Override
    public UserDto updateByCarId(String carId, UserDto userDto) {
        User storedUser = checkUserForExistenceAndThrowNotFound(carId);
        if (!userDto.getCars().isEmpty()) storedUser.setCars(userDto.getCars());
        if (userDto.getType() != null) storedUser.setType(userDto.getType());
        if (userDto.getRole() != null) storedUser.setUserId(userDto.getRole());
        if (userDto.getEmail() != null) storedUser.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            String hashedPassword = passwordEncoder.encode(userDto.getPassword());
            storedUser.setPassword(hashedPassword);
        }
        ;

        return userMapper.toDto(storedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist."));
    }

    private User checkUserForExistenceAndThrowNotFound(String userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(() -> new NotFoundException("User doesn't exist."));
    }
}

