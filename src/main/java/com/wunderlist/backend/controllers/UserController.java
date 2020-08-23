package com.wunderlist.backend.controllers;

import com.wunderlist.backend.models.User;
import com.wunderlist.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /*
    Get a specific user by id
    Route: http://localhost:5280/users/:userid
    */
    @GetMapping(value = "/{userid}", produces = "application/json")
    public ResponseEntity<?> locateUser(@PathVariable long userid) {
        User oneUser = userService.findUserById(userid);

        return new ResponseEntity<>(oneUser, HttpStatus.OK);
    }

    /*
    Create a new user if all required fields are filled in
    Route: http://localhost:5280/users
    */
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody User newuser) throws URISyntaxException {
        newuser.setUserid(0);
        newuser = userService.save(newuser);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    /*
    Replace an entire user
    Route: http://localhost:5280/users/:userid
    */
    @PutMapping(value = "/{userid}", consumes = "application/json")
    public ResponseEntity<?> updateFullUser(@Valid @RequestBody User updateuser, @PathVariable long userid) {
        updateuser.setUserid(userid);
        userService.save(updateuser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Updates a field for a current user
    Route: http://localhost:5280/users/:userid
    */
    @PatchMapping(value = "/{userid}", consumes = "application/json")
    public ResponseEntity<?> updateUserField(@RequestBody User updateuser, @PathVariable long userid) {
        userService.update(updateuser, userid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Deletes a user along with any associated Lists and their associated Items
    Route: http://localhost:5280/users/:userid
    */
    @DeleteMapping(value = "/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable long userid) {
        userService.delete(userid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
