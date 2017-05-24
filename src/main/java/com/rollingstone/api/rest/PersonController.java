package com.rollingstone.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rollingstone.domain.Person;
import com.rollingstone.exception.HTTP404Exception;
import com.rollingstone.service.PersonService;
import com.rollingstone.service.PersonServiceEvent;

@RestController
@RequestMapping(value = "/rsmortgage-personservice/v1/person")
public class PersonController extends AbstractRestHandler {

	@Autowired
	private PersonService personService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json",
			"application/xml" }, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.CREATED)
	public void createPerson(@RequestBody Person person, HttpServletRequest request, HttpServletResponse response) {
		Person createdPerson = this.personService.createPerson(person);
		eventPublisher.publishEvent(new PersonServiceEvent(this, "PersonCreated", createdPerson));
		response.setHeader("Location", request.getRequestURL().append("/").append(createdPerson.getId()).toString());
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Page<Person> getAllPerson(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return this.personService.getAllPersons(page, size);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Person getPerson(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Person person = this.personService.getPerson(id);
		checkResourceFound(person);
		return person;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json",
			"application/xml" }, produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePerson(@PathVariable("id") Long id, @RequestBody Person person, HttpServletRequest request,
			HttpServletResponse response) {
		checkResourceFound(this.personService.getPerson(id));
		if (id != person.getId())
			throw new HTTP404Exception("ID doesn't match!");
		this.personService.updatePerson(person);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePerson(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(this.personService.getPerson(id));
		this.personService.deletePerson(id);
	}
}
