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

    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();

        if(user.getUserid() != 0) {
            userrepos.findById(user.getUserid())
                    .orElseThrow(() -> new EntityNotFoundException("User id: " + user.getUserid() + " was not found.")); // Change to ResourceNotFoundException
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setEmail(user.getEmail().toLowerCase());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());

        /*
        There would be a function here to check a UserRoles jointable to get the correct or default Role for this
        new user, but everyone is getting the same role for this project, and it's already been hard-coded in
        the Users model, so we're skipping:
        for(UserRoles ur : user.getRoles()) {}
        */

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long userid) {
        User currentUser = userrepos.findById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User id: " + userid + " was not found.")); // Change exception to ResourceNotFoundException later

        if(user.getUsername() != null) currentUser.setUsername(user.getUsername().toLowerCase());
        if(user.getPassword() != null) currentUser.setPasswordNoEncrypt(user.getPassword());
        if(user.getEmail() != null) currentUser.setEmail(user.getEmail().toLowerCase());
        if(user.getFirstname() != null) currentUser.setFirstname(user.getFirstname());
        if(user.getLastname() != null) currentUser.setLastname(user.getLastname());

        // Again, the Sprint Challenge method involved roleService to add roles for authentication purposes here.
        // But now I have everyone's default Role hard-coded in the Users model, so skipping this part here.

        return userrepos.save(currentUser);
    }

    @Transactional
    @Override
    public void delete(long userid) {
        userrepos.findById(userid)
                .orElseThrow(() -> new EntityNotFoundException("User id: " + userid + " was not found.")); // Change exception to ResourceNotFoundException
        userrepos.deleteById(userid);
    }
}
