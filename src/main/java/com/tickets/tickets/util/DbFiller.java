package com.tickets.tickets.util;

import com.tickets.tickets.model.Person;
import com.tickets.tickets.model.Ticket;
import com.tickets.tickets.model.TrafficOffence;
import com.tickets.tickets.service.PersonService;
import com.tickets.tickets.service.TicketService;
import com.tickets.tickets.service.TrafficOffenceService;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class DbFiller {
    private final PersonService personService;
    private final TrafficOffenceService trafficOffenceService;
    private final TicketService ticketService;

    public DbFiller(PersonService personService, TrafficOffenceService trafficOffenceService, TicketService ticketService) {
        this.personService = personService;
        this.trafficOffenceService = trafficOffenceService;
        this.ticketService = ticketService;
    }

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void fill() {
        personService.save(new Person(null, "56071155727", "Adam", "Dobrze Wkladam", "test1@gmail.com"));
        personService.save(new Person(null, "05232829959", "Adam", "Dobrze Wladam", "test2@gmail.com"));
        personService.save(new Person(null, "78052253875", "Adam", "Dobrze Kodze", "test3@gmail.com"));
        TrafficOffence trafficOffence = new TrafficOffence(12, 4, "TEST");
        trafficOffenceService.save(trafficOffence);
        Ticket ticket = new Ticket("56071155727", LocalDate.now());
        ticket.getTrafficOffenceList().add(trafficOffence);
        Thread.sleep(1000);
        ticketService.save(ticket);

        trafficOffenceService.save(new TrafficOffence(3, 500, "BELTS"));
        trafficOffenceService.save(new TrafficOffence(3, 500, "SPEED"));
        trafficOffenceService.save(new TrafficOffence(3, 500, "UNSAFE DRIVE"));
        trafficOffenceService.save(new TrafficOffence(3, 500, "DRIFTING"));
    }

}
