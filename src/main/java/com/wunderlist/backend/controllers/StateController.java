package com.wunderlist.backend.controllers;

import com.wunderlist.backend.models.State;
import com.wunderlist.backend.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StateController {
    @Autowired
    private StateService stateService;

    /*
    Get a list of all statuses
    Route: http://localhost:5280/status
    */
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> listAllStatuses() {
        List<State> allStatuses = stateService.findAllStatuses();

        return new ResponseEntity<>(allStatuses, HttpStatus.OK);
    }

    /*
    Get a a specific status
    Route: http://localhost:5280/status/:statusid
    */
    @GetMapping(value = "/{statusid}", produces = "application/json")
    public ResponseEntity<?> showStatus(@PathVariable long statusid) {
        State currentStatus = stateService.findStatusById(statusid);

        return new ResponseEntity<>(currentStatus, HttpStatus.OK);
    }

    /*
    These states will all be preset in SeedData
    So no POST, PUT, PATCH, or DELETE needed here
    */
}
