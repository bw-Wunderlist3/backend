package com.wunderlist.backend.services;

import com.wunderlist.backend.exceptions.ResourceNotFoundException;
import com.wunderlist.backend.models.*;
import com.wunderlist.backend.repository.TodolistRepository;
import com.wunderlist.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userrepos;

    @Autowired
    private TodolistRepository todorepos;

    @Autowired
    private TodolistService todolistService;

    @Override
    public List<User> findAllUsers() {
        List<User> listofUsers = new ArrayList<>();
        userrepos.findAll().iterator().forEachRemaining(listofUsers::add);

        return listofUsers;
    }

    @Override
    public User findUserById(long userid) {
        return userrepos.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + userid + " was not found."));
    }

    @Override
    public User findUserByUsername(String username) {
        User ux = userrepos.findByUsername(username.toLowerCase());
        if(ux == null) throw new ResourceNotFoundException("Username: " + username + " was not found.");

        return ux;
    }

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();

        if(user.getUserid() != 0) {
            userrepos.findById(user.getUserid())
                    .orElseThrow(() -> new ResourceNotFoundException("User id: " + user.getUserid() + " was not found."));
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setEmail(user.getEmail().toLowerCase());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());

        newUser.getTodolists().clear();
        for(Todolist passedTodo : user.getTodolists()) {
            Todolist newTd = new Todolist();
            newTd.setUser(newUser);
            newTd.setTitle(passedTodo.getTitle());

            newTd.getItems().clear();
            for(Item i : passedTodo.getItems()) {
                Item createdItem = new Item();

                State defaultStatus = new State();
                defaultStatus.setStatusid(1); // ID 1 should always be the id for State with a value of "Pending"

                createdItem.setName(i.getName());
                createdItem.setDescription(i.getDescription());
                createdItem.setDuedate(i.getDuedate());
                createdItem.setFrequency(i.getFrequency());
                createdItem.setTodolist(newTd);

                createdItem.getStates().add(new Itemstate(createdItem, defaultStatus));

                newTd.getItems().add(createdItem);
            }

            newUser.getTodolists().add(newTd);
        }

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long userid) {
        User currentUser = userrepos.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + userid + " was not found."));

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
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + userid + " was not found."));
        userrepos.deleteById(userid);
    }
}
