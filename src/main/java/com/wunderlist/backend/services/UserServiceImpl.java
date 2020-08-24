package com.wunderlist.backend.services;

import com.wunderlist.backend.exceptions.ResourceNotFoundException;
import com.wunderlist.backend.models.Item;
import com.wunderlist.backend.models.Todolist;
import com.wunderlist.backend.models.User;
import com.wunderlist.backend.repository.TodolistRepository;
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

    @Autowired
    private TodolistRepository todorepos;

    @Autowired
    private TodolistService todolistService;

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

        newUser.getTodolists().clear();
        for(Todolist passedTodo : user.getTodolists()) {
            Todolist newTd = new Todolist();
            newTd.setUser(newUser);
            newTd.setTitle(passedTodo.getTitle());

            newTd.getItems().clear();
            for(Item i : passedTodo.getItems()) {
                Item createdItem = new Item();

                createdItem.setName(i.getName());
                createdItem.setDescription(i.getDescription());
                createdItem.setDuedate(i.getDuedate());
                createdItem.setFrequency(i.getFrequency());
                createdItem.setTodolist(newTd);

                newTd.getItems().add(createdItem);
            }

            newUser.getTodolists().add(newTd);

            System.out.println("Todo Title: " + passedTodo.getTitle());
            System.out.println("Todo's Items: " + passedTodo.getItems().size());
            System.out.println("Todo's User: " + newUser.getUsername());

            /*
            Todolist newTodo = new Todolist();

            if(passedTodo.getTodoid() != 0) {
                todorepos.findById(passedTodo.getTodoid())
                        .orElseThrow(() -> new ResourceNotFoundException("Grrrr!"));
                newTodo.setTodoid(passedTodo.getTodoid());
            }

            newTodo.setTitle(passedTodo.getTitle());
            newTodo.setUser(passedTodo.getUser());

            newTodo.getItems().clear();
            for(Item i : passedTodo.getItems()) {
                newTodo.getItems().add(new Item(i.getName(), i.getDescription(), i.getDuedate(), i.getFrequency(), newTodo));
            }
            */
        }


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
