package com.tickets.tickets.service;

import com.tickets.tickets.exception.PersonNotFoundExceprion;
import com.tickets.tickets.model.Person;
import com.tickets.tickets.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person findPersonById(Long id) {
        return personRepository.findById(id).orElseThrow(PersonNotFoundExceprion::new);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

}
