package com.tickets.tickets.service;

import com.tickets.tickets.config.TicketProperties;
import com.tickets.tickets.exception.ExceededAmountOfPointException;
import com.tickets.tickets.exception.PeselNotFoundException;
import com.tickets.tickets.exception.TicketNotFoundException;
import com.tickets.tickets.model.Person;
import com.tickets.tickets.model.Ticket;
import com.tickets.tickets.model.TrafficOffence;
import com.tickets.tickets.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final PersonService personService;
    private final MailService mailService;
    private final TicketProperties ticketProperties;

    public TicketService(TicketRepository ticketRepository, PersonService personService, MailService mailService, TicketProperties ticketProperties) {
        this.ticketRepository = ticketRepository;
        this.personService = personService;
        this.mailService = mailService;
        this.ticketProperties = ticketProperties;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getAllTickets(@PESEL String pesel) {
        return ticketRepository.findAllByPesel(pesel);
    }

    public int getTotalNumberOfPersonPoints(String pesel) {
        return getAllTickets(pesel).stream()
                .mapToInt(ticket -> ticket.getTrafficOffenceList()
                        .stream()
                        .mapToInt(TrafficOffence::getNumberOfPoints).sum())
                .sum();
    }

    public int getTotalNumberOfPointsFromTicket(Ticket ticket) {
        return ticket.getTrafficOffenceList().stream()
                .mapToInt(TrafficOffence::getNumberOfPoints)
                .sum();
    }

    public int getTotalTicketPrice(Ticket ticket) {
        List<TrafficOffence> trafficOffenceList = ticket.getTrafficOffenceList();
        return trafficOffenceList.stream().mapToInt(TrafficOffence::getTicketValue).sum();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));
    }

    @Transactional(rollbackFor = Throwable.class)
    public Ticket save(Ticket ticket) {
        if (getTotalNumberOfPersonPoints(ticket.getPesel()) >= ticketProperties.getMaxPointsPerYear()){
            throw new ExceededAmountOfPointException("Person has already lost a driving licence cannot make another ticket!");
        }
        if (!personService.isPeselInDataBase(ticket.getPesel())) {
            throw new PeselNotFoundException(ticket.getPesel());
        }
        ticket.setTotalTicketPrice(getTotalTicketPrice(ticket));
        Person person = personService.findPersonByPesel(ticket.getPesel());

        if (countPointsFromCurrentYear(ticket.getPesel()) + getTotalNumberOfPointsFromTicket(ticket) > ticketProperties.getMaxPointsPerYear()) {
            //todo emailService implementation cant be done yet due to new google policy
//            mailService.sendEmail(person.getEmail(), ticketProperties.getSubject(), ticketProperties.getText());
            log.info("Email has been sent to : " + person.getEmail());
        }
        return ticketRepository.save(ticket);
    }

    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    public int countPointsFromCurrentYear(String pesel) {
        return ticketRepository.findAllByPesel(pesel).stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getLocalDate().isAfter(LocalDate.now().minusYears(1)))
                .mapToInt(c -> c.getTrafficOffenceList()
                        .stream()
                        .filter(Objects::nonNull)
                        .mapToInt(TrafficOffence::getNumberOfPoints)
                        .sum())
                .sum();
    }
}
