package com.tickets.tickets.model;

import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PESEL
    @NonNull
    private String pesel;

    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent
    private LocalDate localDate;

    private int totalTicketPrice;
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<TrafficOffence> trafficOffenceList = new ArrayList<>();

}
