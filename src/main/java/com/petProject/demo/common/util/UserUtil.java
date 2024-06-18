package com.petProject.demo.common.util;

import com.petProject.demo.dto.UserDto;
import com.petProject.demo.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserUtil {
    public List<User> paginateList(Integer page, Integer size, List<User> users) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 1;
        }

        Integer firstUser = size * page + 1;
        Integer lastUser = size * page + 1 + size;

        return users.subList(firstUser, lastUser);
    };
}
