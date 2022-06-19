package com.tickets.tickets.controller;

import com.tickets.tickets.service.TrafficOffenceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/offence")
public class TrafficOffenceController {


    private final TrafficOffenceService trafficOffenceService;

    public TrafficOffenceController(TrafficOffenceService trafficOffenceService) {
        this.trafficOffenceService = trafficOffenceService;
    }
    //todo to be implemented

}
