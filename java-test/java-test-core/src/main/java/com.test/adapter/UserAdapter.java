package com.test.adapter;

import com.test.domain.Person;
import com.test.dto.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Person toDo(UserRequest user) {

        Person person = new Person();
        person.setUsername(user.getUsername());
        person.setPhone(user.getPhone());
        person.setPassword(user.getPassword());

        return person;
    }
}
