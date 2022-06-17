package com.tickets.tickets.service;

import com.tickets.tickets.exception.TrafficOffenceNotFoundException;
import com.tickets.tickets.model.TrafficOffence;
import com.tickets.tickets.repository.PersonRepository;
import com.tickets.tickets.repository.TrafficOffenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficOffenceService {

    private final TrafficOffenceRepository trafficOffenceRepository;

    public TrafficOffenceService(TrafficOffenceRepository trafficOffenceRepository) {
        this.trafficOffenceRepository = trafficOffenceRepository;
    }

    public TrafficOffence save(TrafficOffence trafficOffence) {
        return trafficOffenceRepository.save(trafficOffence);
    }

    public void delete(Long id){
        trafficOffenceRepository.deleteById(id);
    }

    public TrafficOffence findById(Long id) {
        return trafficOffenceRepository.findById(id).orElseThrow(TrafficOffenceNotFoundException::new);
    }

    public List<TrafficOffence> findAll(){
        return trafficOffenceRepository.findAll();
    }

}
