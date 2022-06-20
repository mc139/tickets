package com.tickets.tickets.controller;

import com.tickets.tickets.model.Ticket;
import com.tickets.tickets.service.TicketService;
import com.tickets.tickets.service.TrafficOffenceService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Transient;
import java.util.List;

@RestController()
@RequestMapping("api/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final TrafficOffenceService trafficOffenceService;

    public TicketController(TicketService ticketService, TrafficOffenceService trafficOffenceService) {
        this.ticketService = ticketService;
        this.trafficOffenceService = trafficOffenceService;
    }

    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/all/pesel/{pesel}")
    public List<Ticket> getAllTicketsByPesel(@PathVariable String pesel) {
        return ticketService.getAllTickets(pesel);
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    @Transient
    public Ticket addTicket(@RequestBody Ticket ticket) {
        ticket.getTrafficOffenceList().forEach(trafficOffenceService::save);
        return ticketService.save(ticket);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteById(id);
    }
}
