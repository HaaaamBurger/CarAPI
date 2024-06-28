package com.petProject.demo.service;

import java.util.List;

import com.petProject.demo.common.type.UserSchema;
import com.petProject.demo.common.util.UserUtil;
import com.petProject.demo.model.Car;
import com.petProject.demo.security.exception.EntityAlreadyExistsException;
import com.petProject.demo.security.exception.NotFoundException;
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
public class UserService implements UserDetailsService, UserSchema {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        checkUserForExistenceAndThrowNotFound(userDto.getUserId());
        userRepository.save(userMapper.fromDto(userDto));

        return null;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto getByCarId(String carId) {
        return null;
    }

    @Override
    public UserDto removeByCarId(String carId) {
        return null;
    }

    @Override
    public UserDto updateByCarId(String carId, UserDto userDto) {
        return null;
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
