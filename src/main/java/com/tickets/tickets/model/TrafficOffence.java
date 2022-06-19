package com.tickets.tickets.model;

import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor
public class TrafficOffence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private int numberOfPoints;
    @NonNull
    private int ticketValue;

    @NonNull
    @ManyToOne
    private OffenceType offenceType;


}
