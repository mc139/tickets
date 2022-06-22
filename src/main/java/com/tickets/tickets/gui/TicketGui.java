package com.tickets.tickets.gui;

import com.tickets.tickets.model.Ticket;
import com.tickets.tickets.model.TrafficOffence;
import com.tickets.tickets.service.PersonService;
import com.tickets.tickets.service.TicketService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringComponent
@UIScope
@Route("gui/tickets")
public class TicketGui extends VerticalLayout {

    private final TicketService ticketService;
    private final TicketForm ticketForm;
    private final TrafficOffenceForm trafficOffenceForm;
    private final Button create = new Button("CREATE TICKET");
    private final PersonService personService;

    public TicketGui(TicketService ticketService, TicketForm ticketForm, TrafficOffenceForm trafficOffenceForm, PersonService personService) {
        this.ticketService = ticketService;
        this.ticketForm = ticketForm;
        this.trafficOffenceForm = trafficOffenceForm;
        this.personService = personService;
        trafficOffenceForm.setSizeFull();
        add(ticketForm, trafficOffenceForm, create);
        create.addClickListener(click -> createTicket());
    }

    private void createTicket() {
        if (isTicketValid(15, 15000)) {
            Ticket ticket = new Ticket(ticketForm.getPesel(),ticketForm.getDate());
            ticket.setTrafficOffenceList(trafficOffenceForm.getSelectedItems());
            ticketService.save(ticket);
            log.info("Ticket has been saved to DB , this person has :" + ticketService.getTotalNumberOfPoints(ticket.getPesel()) + "points");
        }

    }

    private boolean isTicketValid(int maximumNumberOfPoints, int maximumTicketPrice) {
        boolean rtn = false;
        if (!personService.isPeselInDataBase(ticketForm.getPesel())) {
            Notification.show("Sorry this PESEL is not in database").setPosition(Notification.Position.MIDDLE);
        } else if (getTotalAmountOfPoints() >= maximumNumberOfPoints) {
            Notification.show("Sorry maximum amount of points is :" + maximumNumberOfPoints).setPosition(Notification.Position.MIDDLE);
        } else if (getTotalTicketPrice() > maximumTicketPrice) {
            Notification.show("Sorry maximum ticket price is :" + maximumTicketPrice).setPosition(Notification.Position.MIDDLE);
        } else {
            rtn = true;
        }
        return rtn;
    }

    private int getTotalTicketPrice() {
        return trafficOffenceForm.getSelectedItems().stream()
                .mapToInt(TrafficOffence::getTicketValue)
                .sum();
    }

    private int getTotalAmountOfPoints() {
        return trafficOffenceForm.getSelectedItems().stream()
                .mapToInt(TrafficOffence::getNumberOfPoints)
                .sum();
    }


}
