/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test;

import com.test.controller.PersonController;
import com.test.repository.PersonRepository;
import com.test.service.PersonService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonController personController;

    @Value("${test.any_prop}")
    String anyProp;


    @Before
    public void deleteAllBeforeTests() throws Exception {
        personRepository.deleteAll();
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(personController).isNotNull();
    }

    @Ignore
    @Test
    public void shouldReturnRepositoryIndex() throws Exception {

        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
                jsonPath("$._links.people").exists());
    }

    @Ignore
    @Test
    public void shouldCreateEntity() throws Exception {

        mockMvc.perform(post("/people").content(
                "{\"username\": \"Frodo\", \"phone\":\"Baggins\"}")).andExpect(
                status().isCreated()).andExpect(
                header().string("Location", containsString("people/")));
    }

    @Ignore
    @Test
    public void shouldRetrieveEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/people").content(
                "{\"username\": \"Frodo\", \"phone\":\"Baggins\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
                jsonPath("$.username").value("Frodo")).andExpect(
                jsonPath("$.phone").value("Baggins"));
    }

    @Ignore
    @Test
    public void shouldQueryEntity() throws Exception {

        mockMvc.perform(post("/people").content(
                "{ \"username\": \"Frodo\", \"phone\":\"Baggins\"}")).andExpect(
                status().isCreated());

        mockMvc.perform(
                get("/people/search/findByPhone?phone={phone}", "Baggins")).andExpect(
                status().isOk()).andExpect(
                jsonPath("$._embedded.people[0].username").value(
                        "Frodo"));
    }

    @Ignore
    @Test
    public void shouldUpdateEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/people").content(
                "{\"username\": \"Frodo\", \"phone\":\"Baggins\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");

        mockMvc.perform(put(location).content(
                "{\"username\": \"Bilbo\", \"phone\":\"Baggins\"}")).andExpect(
                status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
                jsonPath("$.username").value("Bilbo")).andExpect(
                jsonPath("$.phone").value("Baggins"));
    }

    @Ignore
    @Test
    public void shouldPartiallyUpdateEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/people").content(
                "{\"username\": \"Frodo\", \"phone\":\"Baggins\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");

        mockMvc.perform(
                patch(location).content("{\"username\": \"Bilbo Jr.\"}")).andExpect(
                status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
                jsonPath("$.username").value("Bilbo Jr.")).andExpect(
                jsonPath("$.phone").value("Baggins"));
    }

    @Ignore
    @Test
    public void shouldDeleteEntity() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/people").content(
                "{ \"username\": \"Bilbo\", \"phone\":\"Baggins\"}")).andExpect(
                status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }
}