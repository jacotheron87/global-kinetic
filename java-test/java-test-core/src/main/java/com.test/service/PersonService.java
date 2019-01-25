package com.test.service;

import com.test.adapter.PersonAdapter;
import com.test.adapter.UserAdapter;
import com.test.domain.Person;
import com.test.dto.LoginResponse;
import com.test.dto.User;
import com.test.dto.UserRequest;
import com.test.dto.UsersResponse;
import com.test.repository.PersonRepository;
import com.test.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PersonService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private PersonAdapter personAdapter;
    private UserAdapter userAdapter;
    private PersonRepository personRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JWTUtils jwtUtils;

    @Autowired
    public PersonService(PersonAdapter personAdapter, UserAdapter userAdapter, PersonRepository personRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JWTUtils jwtUtils) {
        this.personAdapter = personAdapter;
        this.userAdapter = userAdapter;
        this.personRepository = personRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public void saveUser(UserRequest user) {
        Person person = userAdapter.toDo(user);
        logger.info(person.toString());
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        logger.info(person.toString());
        personRepository.save(person);
    }

    public UsersResponse getUsers() {

        UsersResponse usersResponse = new UsersResponse();

        List<User> users = new ArrayList<>();

        Iterable<Person> repositoryAll = personRepository.findAll();
        Iterator<Person> personIterator = repositoryAll.iterator();
        while (personIterator.hasNext()) {
            Person person = personIterator.next();
            logger.info("Person - " + person.toString());
            User user = personAdapter.toDTO(person);
            logger.info("User - " + user.toString());
            users.add(user);
        }

        usersResponse.setUsers(users);

        return usersResponse;

    }

    public LoginResponse login(UserRequest userRequest){
        LoginResponse loginResponse = new LoginResponse();
        Person user = personRepository.findByUsername(userRequest.getUsername());
        if (user != null){
            Boolean match = bCryptPasswordEncoder.matches(userRequest.getPassword(),user.getPassword());
            logger.info(match.toString());
            if (bCryptPasswordEncoder.matches(userRequest.getPassword(),user.getPassword())) {
                loginResponse.setId(user.getId());
                loginResponse.setToken(jwtUtils.authenticate(userRequest));
            }
        }
        return loginResponse;
    }
}
