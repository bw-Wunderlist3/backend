package com.wunderlist.backend.services;

import com.wunderlist.backend.models.Todolist;

import java.util.List;

public interface TodolistService {
    List<Todolist> findAllLists();

    Todolist findListById(long todoid);

    Todolist saveList(Todolist todo);

    void deleteList(long todoid);
}
