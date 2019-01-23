package com.test;

import java.util.List;

import com.test.domain.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends PagingAndSortingRepository<Person, String> {

	List<Person> findByUsername(@Param("username") String username);

	List<Person> findByPhone(@Param("phone") String phone);

}
