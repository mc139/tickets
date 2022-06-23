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

        trafficOffenceService.save(new TrafficOffence(4, 500, "Driving without due care and attention"));
        trafficOffenceService.save(new TrafficOffence(5, 500, "Driving without due care and attention or without reasonable consideration for other road users"));
        trafficOffenceService.save(new TrafficOffence(7, 300, "Driving without reasonable consideration for other road users"));
        trafficOffenceService.save(new TrafficOffence(2, 450, "Causing death by careless driving then failing to supply a specimen for alcohol analysis"));
        trafficOffenceService.save(new TrafficOffence(4, 560, "Failing to stop after an accident"));
        trafficOffenceService.save(new TrafficOffence(5, 570, "Failing to give particulars or report an accident within 24 hours"));
        trafficOffenceService.save(new TrafficOffence(6, 640, "Undefined accident offences"));
        trafficOffenceService.save(new TrafficOffence(3, 120, "Driving while disqualified by order of court"));
        trafficOffenceService.save(new TrafficOffence(7, 500, "Attempting to drive while disqualified by order of court"));
        trafficOffenceService.save(new TrafficOffence(10, 1500, "Causing death by driving while disqualified"));
        trafficOffenceService.save(new TrafficOffence(9, 1200, "Causing serious injury by driving while disqualified"));
    }

}
