package com.wunderlist.backend.repository;

import com.wunderlist.backend.models.Todolist;
import org.springframework.data.repository.CrudRepository;

public interface TodolistRepository extends CrudRepository<Todolist, Long> {
}
