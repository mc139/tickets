package com.tickets.tickets.controller;

import com.tickets.tickets.model.Person;
import com.tickets.tickets.model.Ticket;
import com.tickets.tickets.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/ticket")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
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
    public Ticket getTicketById(@PathVariable Long id){
        return ticketService.getTicketById(id);
    }

    @PostMapping
    public void addTicket(@RequestBody Ticket ticket){
        ticketService.save(ticket);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTicket(@PathVariable Long id){
        ticketService.deleteById(id);
    }
}
