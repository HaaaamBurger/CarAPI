package com.petProject.demo.common.util;

import com.petProject.demo.model.User;
import com.petProject.demo.repository.UserRepository;
import com.petProject.demo.security.exception.UserAlreadyExistsException;
import com.petProject.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;


    ;
};
