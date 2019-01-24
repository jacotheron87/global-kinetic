package com.test.adapter;

import com.test.domain.Person;
import com.test.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PersonAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public User toDTO(Person person) {

        User user = new User();
        user.setId(person.getId());
        user.setPhone(person.getPhone());

        return user;
    }
}
