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
import java.util.List;

@RestController
//@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /*
    Get a list of all users
    Route: http://localhost:5280/users
    */
    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<?> locateAllUsers() {
        List<User> allUsers = userService.findAllUsers();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    /*
    Get a specific user by id
    Route: http://localhost:5280/users/:userid
    */
    @GetMapping(value = "/users/{userid}", produces = "application/json")
    public ResponseEntity<?> locateUser(@PathVariable long userid) {
        User oneUser = userService.findUserById(userid);

        return new ResponseEntity<>(oneUser, HttpStatus.OK);
    }

    /*
    Get a specific user by id
    Route: http://localhost:5280/users/:username
    */
    @GetMapping(value = "/username/{username}", produces = "application/json")
    public ResponseEntity<?> fetchUserByName(@PathVariable String username) {
        User namedUser = userService.findUserByUsername(username);

        return new ResponseEntity<>(namedUser, HttpStatus.OK);
    }

    /*
    Create a new user if all required fields are filled in
    Route: http://localhost:5280/users/register
    */
    @PostMapping(value = "/users/register", consumes = "application/json")
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
    Route: http://localhost:5280/changeuser/:userid
    */
    @PutMapping(value = "/changeuser/{userid}", consumes = "application/json")
    public ResponseEntity<?> updateFullUser(@Valid @RequestBody User updateuser, @PathVariable long userid) {
        updateuser.setUserid(userid);
        userService.save(updateuser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Note: formerly @PatchMapping
    Updates a field for a current user
    Route: http://localhost:5280/users/:userid
    */
    @PutMapping(value = "/users/{userid}", consumes = "application/json")
    public ResponseEntity<?> updateUserField(@RequestBody User updateuser, @PathVariable long userid) {
        userService.update(updateuser, userid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Deletes a user along with any associated Lists and their associated Items
    Route: http://localhost:5280/users/:userid
    */
    @DeleteMapping(value = "/users/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable long userid) {
        userService.delete(userid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
