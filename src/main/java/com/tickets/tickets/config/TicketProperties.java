package com.tickets.tickets.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("ticket")
@Getter
@Setter
@Component
public class TicketProperties {

    @Value("${ticket.maxPointsPerYear}")
    private int maxPointsPerYear;

    @Value("${ticket.subject}")
    private String subject;

    @Value("${ticket.text}")
    private String text;


}
