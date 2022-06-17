package com.tickets.tickets.service;

import com.tickets.tickets.model.OffenceType;
import com.tickets.tickets.repository.OffenceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffenceTypeService {

    private OffenceTypeRepository offenceTypeRepository;

    public OffenceTypeService(OffenceTypeRepository offenceTypeRepository) {
        this.offenceTypeRepository = offenceTypeRepository;
    }


    public void save(OffenceType offenceType){
        offenceTypeRepository.save(offenceType);
    }

    public List<OffenceType> getAll(){
        return offenceTypeRepository.findAll();
    }
}
