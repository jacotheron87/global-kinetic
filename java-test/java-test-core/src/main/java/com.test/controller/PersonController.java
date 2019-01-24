package com.test.controller;

import com.test.dto.UserRequest;
import com.test.dto.UsersResponse;
import com.test.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PersonController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PutMapping(value = "/users", produces = "application/json")
    public ResponseEntity<Object> saveUser(@RequestBody UserRequest userRequest) {
        personService.saveUser(userRequest);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<Object> getUsers() {

        UsersResponse usersResponse = personService.getUsers();

        return new ResponseEntity<Object>(usersResponse, HttpStatus.OK);
    }
}
