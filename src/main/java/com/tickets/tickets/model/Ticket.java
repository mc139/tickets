package com.tickets.tickets.model;

import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PESEL
    @NonNull
    private String pesel;

    @NonNull
    private LocalDate localDate;
    @OneToMany
    private List<TrafficOffence> trafficOffenceList = new ArrayList<>();

    @OneToOne
    private Person person;
}
