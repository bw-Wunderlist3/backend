package com.wunderlist.backend.services;

import com.wunderlist.backend.models.Todolist;
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

    //fixme
    @Transactional
    @Override
    public Todolist saveList(Todolist todo) {
        Todolist newlist = new Todolist();

        return null;
    }

    @Transactional
    @Override
    public void deleteList(long todoid) {
        todorepos.findById(todoid)
                .orElseThrow(() -> new EntityNotFoundException("Todo List id: " + todoid + " was not found.")); // Change to ResponseNotFoundException later
        todorepos.deleteById(todoid);
    }
}
