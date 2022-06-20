package com.tickets.tickets.controller;

import com.tickets.tickets.model.Person;
import com.tickets.tickets.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/person")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/all")
    public List<Person> getAllPeople() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.findPersonById(id);
    }

    @GetMapping("/pesel/{pesel}")
    public Person getPersonByPesel(@PathVariable String pesel){
        return personService.findPersonByPesel(pesel);
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person){
       return personService.save(person);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.deletePerson(id);
    }

}
