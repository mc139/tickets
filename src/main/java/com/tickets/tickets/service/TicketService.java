package com.tickets.tickets.service;

import com.tickets.tickets.exception.PeselNotFoundException;
import com.tickets.tickets.exception.TicketNotFoundException;
import com.tickets.tickets.model.Ticket;
import com.tickets.tickets.model.TrafficOffence;
import com.tickets.tickets.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final PersonService personService;

    public TicketService(TicketRepository ticketRepository, PersonService personService) {
        this.ticketRepository = ticketRepository;
        this.personService = personService;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getAllTickets(@PESEL String pesel) {
        return ticketRepository.findAllByPesel(pesel);
    }

    public int getTotalNumberOfPoints(String pesel){
       return getAllTickets(pesel).stream()
                .mapToInt(ticket-> ticket.getTrafficOffenceList()
                .stream()
                        .mapToInt(TrafficOffence::getNumberOfPoints).sum())
                .sum();
    }

    public int getTotalTicketPrice(Ticket ticket){
        List<TrafficOffence> trafficOffenceList = ticket.getTrafficOffenceList();
        return trafficOffenceList.stream().mapToInt(TrafficOffence::getTicketValue).sum();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));
    }

    @Transactional(rollbackFor = Throwable.class)
    public Ticket save(Ticket ticket) {
        if (!personService.isPeselInDataBase(ticket.getPesel())) {
            throw new PeselNotFoundException(ticket.getPesel());
        }
        ticket.setTotalTicketPrice(getTotalTicketPrice(ticket));
        return ticketRepository.save(ticket);
    }

    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }
}
