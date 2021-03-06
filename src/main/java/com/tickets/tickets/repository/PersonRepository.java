package com.tickets.tickets.repository;

import com.tickets.tickets.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByPesel(String pesel);

}
