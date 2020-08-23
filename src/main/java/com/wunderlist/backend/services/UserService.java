package com.wunderlist.backend.services;

import com.wunderlist.backend.models.User;

public interface UserService {
    User findUserById(long userid);

    User save(User user);

    User update(User user, long userid);

    void delete(long userid);
}
