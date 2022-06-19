package com.tickets.tickets.util;

import com.tickets.tickets.model.OffenceType;
import com.tickets.tickets.model.Person;
import com.tickets.tickets.model.Ticket;
import com.tickets.tickets.model.TrafficOffence;
import com.tickets.tickets.service.OffenceTypeService;
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
    private PersonService personService;
    private TrafficOffenceService trafficOffenceService;
    private OffenceTypeService offenceTypeService;
    private TicketService ticketService;

    public DbFiller(PersonService personService, TrafficOffenceService trafficOffenceService, OffenceTypeService offenceTypeService, TicketService ticketService) {
        this.personService = personService;
        this.trafficOffenceService = trafficOffenceService;
        this.offenceTypeService = offenceTypeService;
        this.ticketService = ticketService;
    }

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void fill() {
        personService.save(new Person(null, "56071155727", "Adam", "Dobrze Wkladam", "test1@gmail.com"));
        personService.save(new Person(null, "05232829959", "Adam", "Dobrze Wladam", "test2@gmail.com"));
        personService.save(new Person(null, "78052253875", "Adam", "Dobrze Kodze", "test3@gmail.com"));
        OffenceType speeding = new OffenceType(null, "SPEEDING");
        offenceTypeService.save(speeding);
        OffenceType belt = new OffenceType(null, "BELT");
        offenceTypeService.save(belt);
        TrafficOffence trafficOffence = new TrafficOffence(4, 4, speeding);
        trafficOffenceService.save(trafficOffence);
        Ticket ticket = new Ticket("56071155727", LocalDate.now());
        ticket.getTrafficOffenceList().add(trafficOffence);
        ticket.setPerson(personService.findPersonByPesel(ticket.getPesel()));


        Thread.sleep(1000);
        ticketService.save(ticket);

        ticketService.save(new Ticket("90111695756", LocalDate.now()));

    }


}
