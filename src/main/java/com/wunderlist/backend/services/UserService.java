package com.wunderlist.backend.services;

import com.wunderlist.backend.models.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User findUserById(long userid);

    User findUserByUsername(String username);

    User save(User user);

    User update(User user, long userid);

    void delete(long userid);
}
