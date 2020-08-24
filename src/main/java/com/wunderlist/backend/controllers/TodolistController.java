package com.wunderlist.backend.controllers;

import com.wunderlist.backend.models.Todolist;
import com.wunderlist.backend.services.TodolistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodolistController {
    @Autowired
    TodolistService todolistService;

    /*
    List all Todolists
    Route: http://localhost:5280/todos
    */
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> listAllTodos() {
        List<Todolist> allTodos = todolistService.findAllLists();

        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }

    /*
    Get a specific Todolist
    http://localhost:5280/todos/:todoid
    */
    @GetMapping(value = "/{todoid}", produces = "application/json")
    public ResponseEntity<?> findaTodo(@PathVariable long todoid) {
        Todolist oneList = todolistService.findListById(todoid);

        return new ResponseEntity<>(oneList, HttpStatus.OK);
    }

    /*
    Create a new Todolist
    http://localhost:5280/todos/u/:userid/t/:title
    */
    @PostMapping(value = "/u/{userid}/t/{title}", consumes = "application/json")
    public ResponseEntity<?> createTodo(@PathVariable long userid, @PathVariable String title) throws URISyntaxException {
        Todolist newtodo = todolistService.saveList(userid, title);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newTodoURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{todoid}")
                .buildAndExpand(newtodo.getTodoid())
                .toUri();
        responseHeaders.setLocation(newTodoURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    /*
    Edit a Todolist (essentially, it's title) by ID
    http://localhost:5280/todos/:todoid/t/:title
    */
    @PutMapping(value = "/{todoid}/t/{title}", consumes = "application/json")
    public ResponseEntity<?> updateTodo(@PathVariable long todoid, @PathVariable String title) {
        todolistService.updateList(todoid, title);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // No PatchMapping needed since it'll literally be no different than the PutMapping above for this table

    /*
    Delete a Todolist and all of it's associated Items
    http://localhost:5280/todos/:todoid
    */
    @DeleteMapping(value = "/{todoid}")
    public ResponseEntity<?> deleteTodo(@PathVariable long todoid) {
        todolistService.deleteList(todoid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
