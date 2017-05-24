package com.rollingstone.service;

import com.rollingstone.dao.jpa.PersonRepository;
import com.rollingstone.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	CounterService counterService;
	

	public PersonService() {
	}

	public Person createPerson(Person person) {
		return personRepository.save(person);
	}

	public Person getPerson(long id) {
		return personRepository.findOne(id);
	}

	public void updatePerson(Person person) {
		personRepository.save(person);
	}

	public void deletePerson(Long id) {
		personRepository.delete(id);
	}

	public Page<Person> getAllPersons(Integer page, Integer size) {
		Page<Person> pageOfPersons = personRepository.findAll(new PageRequest(page, size));
		// example of adding to the /metrics
		if (size > 50) {
			log.info("Large Page Size for getAllPersons");
			counterService.increment("com.rollingstone.PersonService.getAll.largePayload");
		}
		return pageOfPersons;
	}
}
