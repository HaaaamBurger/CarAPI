package com.petProject.demo.service;

import java.util.List;
import java.util.Optional;

import com.petProject.demo.auth.util.AuthUtil;
import com.petProject.demo.common.type.Roles;
import com.petProject.demo.repository.CarRepository;
import com.petProject.demo.security.exception.EntityAlreadyExistsException;
import com.petProject.demo.security.exception.NotFoundException;
import com.petProject.demo.security.exception.UnexpectedUserRoleException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.petProject.demo.common.mapper.UserMapper;
import com.petProject.demo.dto.UserDto;
import com.petProject.demo.model.User;
import com.petProject.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final AuthUtil authUtil;

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    @Transactional
    public UserDto save(UserDto userDto) {
        userRepository.findUserByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new EntityAlreadyExistsException("User already exists.");
        });

        if (!userDto.getRole().equals(String.valueOf(Roles.valueOf(userDto.getRole())))) {
            throw new UnexpectedUserRoleException("Unappropriated role.");
        }

        String hashedPassword = authUtil.passwordEncoder().encode(userDto.getPassword());
        userDto.setPassword(hashedPassword);
        userDto.setType((userDto.getType()));

        User savedUser = userRepository.save(userMapper.fromDto(userDto));

        return userMapper.toDto(savedUser);
    }

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).toList();
    }

    public UserDto getByUserId(String carId) {
        User storedUser = checkUserForExistenceAndThrowNotFound(carId);
        return userMapper.toDto(storedUser);
    }

    public UserDto removeByUserId(String carId) {
        User storedUser = checkUserForExistenceAndThrowNotFound(carId);
        userRepository.delete(storedUser);

        return userMapper.toDto(storedUser);
    }

    public UserDto getUserByEmail(String email) {
        return userMapper.toDto(userRepository.findUserByEmail(email).get());
    }

    public UserDto updateByUserId(String carId, UserDto userDto) {
        User storedUser = checkUserForExistenceAndThrowNotFound(carId);
        if (!userDto.getCars().isEmpty()) storedUser.setCars(userDto.getCars());
        if (userDto.getType() != null) storedUser.setType(userDto.getType());
        if (userDto.getRole() != null) storedUser.setUserId(userDto.getRole());
        if (userDto.getEmail() != null) storedUser.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            String hashedPassword = authUtil.passwordEncoder().encode(userDto.getPassword());
            storedUser.setPassword(hashedPassword);
        }

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

