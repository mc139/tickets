package com.tickets.tickets.model;

import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @PESEL
    private String pesel;
    private String name;
    private String surname;
    @Email
    private String email;

}
