package com.tickets.tickets.service;

import com.tickets.tickets.exception.PersonNotFoundException;
import com.tickets.tickets.exception.PeselNotFoundException;
import com.tickets.tickets.model.Person;
import com.tickets.tickets.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public Person findPersonByPesel(String pesel){
      return Optional.ofNullable(personRepository.findByPesel(pesel)).orElseThrow(()-> new PeselNotFoundException(pesel));
    }

    public boolean isPeselInDataBase(String pesel){
       return personRepository.findByPesel(pesel) != null;
    }

}
