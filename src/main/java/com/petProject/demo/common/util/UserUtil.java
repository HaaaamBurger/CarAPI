package com.petProject.demo.common.util;

import com.petProject.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;


    ;
};
