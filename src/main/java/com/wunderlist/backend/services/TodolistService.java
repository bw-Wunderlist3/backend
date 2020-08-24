package com.wunderlist.backend.services;

import com.wunderlist.backend.models.Todolist;

import java.util.List;

public interface TodolistService {
    List<Todolist> findAllLists();

    Todolist findListById(long todoid);

    Todolist saveList(long userid, String title);

    Todolist updateList(long todoid, String title);

    void deleteList(long todoid);
}
