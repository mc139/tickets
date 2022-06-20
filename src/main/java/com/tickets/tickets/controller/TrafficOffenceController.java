package com.tickets.tickets.controller;

import com.tickets.tickets.model.TrafficOffence;
import com.tickets.tickets.service.TrafficOffenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offences")
public class TrafficOffenceController {

    private final TrafficOffenceService trafficOffenceService;

    public TrafficOffenceController(TrafficOffenceService trafficOffenceService) {
        this.trafficOffenceService = trafficOffenceService;
    }

    @PostMapping("/create")
    public TrafficOffence createTrafficOffence(@RequestBody TrafficOffence trafficOffence){
        return trafficOffenceService.save(trafficOffence);
    }

    @GetMapping("/{id}")
    public TrafficOffence findById(@PathVariable Long id){
        return trafficOffenceService.findById(id);
    }

    @GetMapping("/all")
    public List<TrafficOffence> findAll(){
        return trafficOffenceService.findAll();
    }

    @DeleteMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id){
        trafficOffenceService.delete(id);
    }

}
