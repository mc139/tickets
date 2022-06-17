package com.tickets.tickets.util;

import com.tickets.tickets.model.OffenceType;
import com.tickets.tickets.model.Person;
import com.tickets.tickets.model.TrafficOffence;
import com.tickets.tickets.service.OffenceTypeService;
import com.tickets.tickets.service.PersonService;
import com.tickets.tickets.service.TrafficOffenceService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Controller
public class DbFiller {
    private PersonService personService;
    private TrafficOffenceService trafficOffenceService;
    private OffenceTypeService offenceTypeService;

    public DbFiller(PersonService personService, TrafficOffenceService trafficOffenceService, OffenceTypeService offenceTypeService) {
        this.personService = personService;
        this.trafficOffenceService = trafficOffenceService;
        this.offenceTypeService = offenceTypeService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fill(){
        personService.save(new Person(null,"56071155727","Adam","Dobrze Wkladam","test1@gmail.com"));
        personService.save(new Person(null,"05232829959","Adam","Dobrze Wladam","test2@gmail.com"));
        personService.save(new Person(null,"78052253875","Adam","Dobrze Kodze","test3@gmail.com"));
        offenceTypeService.save(new OffenceType(null,"SPEEDING"));
        offenceTypeService.save(new OffenceType(null,"BELT"));
    }

}
