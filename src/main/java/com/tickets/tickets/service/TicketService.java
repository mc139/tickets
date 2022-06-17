package com.tickets.tickets.service;

import com.tickets.tickets.model.Ticket;
import com.tickets.tickets.repository.TicketRepository;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets(){
       return ticketRepository.findAll();
    }

    //todo przechwycic exception
    public List<Ticket> getAllTickets(@PESEL String pesel){
       return ticketRepository.findAllByPesel(pesel);
    }
}
