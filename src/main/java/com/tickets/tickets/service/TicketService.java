package com.tickets.tickets.service;

import com.tickets.tickets.exception.PeselNotFoundException;
import com.tickets.tickets.model.Ticket;
import com.tickets.tickets.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TicketService {
    private TicketRepository ticketRepository;
    private PersonService personService;

    public TicketService(TicketRepository ticketRepository, PersonService personService) {
        this.ticketRepository = ticketRepository;
        this.personService = personService;
    }

    public List<Ticket> getAllTickets(){
       return ticketRepository.findAll();
    }

    public List<Ticket> getAllTickets(@PESEL String pesel){
       return ticketRepository.findAllByPesel(pesel);
    }

    public void save(Ticket ticket) {

        if (personService.isPeselInDataBase(ticket.getPesel())){
            ticketRepository.save(ticket);
        } else {
           log.error("PESEL NOT IN DATA BASE!! : " + ticket.getPesel());
        }

    }
}
