package com.test;

import com.test.domain.Person;
import com.test.dto.User;
import com.test.dto.UserRequest;
import com.test.dto.UsersResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @PutMapping(value = "/users", produces = "application/json")
    public ResponseEntity<Object> saveUser(UserRequest userRequest){
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<Object> getUsers(){

        UsersResponse usersResponse = new UsersResponse();

        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setId("id111");
        user1.setPhone("011");
        users.add(user1);

        User user2 = new User();
        user2.setId("id222");
        user2.setPhone("021");
        users.add(user2);

        usersResponse.setUsers(users);

        return new ResponseEntity<Object>(usersResponse, HttpStatus.OK);
    }
}
