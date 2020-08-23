package com.wunderlist.backend.services;

import com.wunderlist.backend.models.User;
import com.wunderlist.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userrepos;

    @Override
    public User findUserById(long userid) {
        return userrepos.findById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User id: " + userid + " was not found.")); // Change to ResourceNotFoundException
    }

    //fixme
    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();

        /*
        Sprint challenge shows user repository and roles service used here to create the new user and
        assign a default role (for authentication purposes) to the new user. All accounts here will have the same
        role: User. There seems to be no need to create a table to hold one item of data to then join it to the
        Users Table. Still trying to brainstorm a different way to handle this.
        */

        return null; // This needs to change to "return userrepos.save(newUser)" once I figure out how I want to handle authentication roles
    }

    @Transactional
    @Override
    public User update(User user, long userid) {
        User currentUser = userrepos.findById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User id: " + userid + " was not found.")); // Change exception to ResourceNotFoundException later

        // Again, the Sprint Challenge method involved roleService to add roles for authentication purposes here.

        return null; // This needs to change to "return userrepos.save(newUser)" once I figure out how I want to handle authentication roles
    }

    @Transactional
    @Override
    public void delete(long userid) {
        userrepos.findById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User id: " + userid + " was not found.")); // Change exception to ResourceNotFoundException
        userrepos.deleteById(userid);
    }
}
