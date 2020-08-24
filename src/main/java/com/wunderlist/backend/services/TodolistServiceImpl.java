package com.wunderlist.backend.services;

import com.wunderlist.backend.models.Todolist;
import com.wunderlist.backend.models.User;
import com.wunderlist.backend.repository.TodolistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "todolistService")
public class TodolistServiceImpl implements TodolistService {
    @Autowired
    private TodolistRepository todorepos;

    @Autowired
    private UserService userService;

    @Override
    public List<Todolist> findAllLists() {
        List<Todolist> list = new ArrayList<>();
        todorepos.findAll().iterator().forEachRemaining(list::add);

        return list;
    }

    @Override
    public Todolist findListById(long todoid) {
        return todorepos.findById(todoid)
                .orElseThrow(() -> new EntityNotFoundException("Todo List id: " + todoid + " was not found.")); // Change to ResourceNotFoundException
    }

    @Transactional
    @Override
    public Todolist saveList(long userid, String title) {
        User currentUser = userService.findUserById(userid);
        Todolist newlist = new Todolist(currentUser, title);

        return todorepos.save(newlist);
    }

    @Transactional
    @Override
    public Todolist updateList(long todoid, String title) {
        if(todorepos.findById(todoid).isPresent()) {
            Todolist currentList = findListById(todoid);
            currentList.setTitle(title);
            return todorepos.save(currentList);
        } else throw new EntityNotFoundException("Todo List with id: " + todoid + " was not found."); // Change to ResponseNotFoundException later
    }

    @Transactional
    @Override
    public void deleteList(long todoid) {
        todorepos.findById(todoid)
                .orElseThrow(() -> new EntityNotFoundException("Todo List id: " + todoid + " was not found.")); // Change to ResponseNotFoundException later
        todorepos.deleteById(todoid);
    }
}
