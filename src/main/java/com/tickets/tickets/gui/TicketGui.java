package com.tickets.tickets.gui;

import com.tickets.tickets.model.Ticket;
import com.tickets.tickets.service.TicketService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringComponent
@UIScope
@Route("gui/tickets")
public class TicketGui extends VerticalLayout {

    private final TicketService ticketService;
    private final TicketForm ticketForm;
    private final TrafficOffenceForm trafficOffenceForm;
    private Button create = new Button("CREATE TICKET");

    public TicketGui(TicketService ticketService, TicketForm ticketForm, TrafficOffenceForm trafficOffenceForm) {
        this.ticketService = ticketService;
        this.ticketForm = ticketForm;
        this.trafficOffenceForm = trafficOffenceForm;
        trafficOffenceForm.setSizeFull();
        add(ticketForm,trafficOffenceForm, create);
        create.addClickListener(click -> createTicket());
    }

    private void createTicket() {
        Ticket ticket = new Ticket(ticketForm.getPesel());
        ticket.setTrafficOffenceList(trafficOffenceForm.getSelectedItems());
        ticketService.save(ticket);

    }
}
