package com.rollingstone.dao.jpa;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.rollingstone.domain.Person;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
	Person findPersopnByage(int age);

	List<Person> findPersonByLastName(String lastName);

	Page<Person> findAll(Pageable pageable);
}
