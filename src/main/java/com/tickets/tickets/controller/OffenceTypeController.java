package com.tickets.tickets.controller;

import com.tickets.tickets.model.OffenceType;
import com.tickets.tickets.service.OffenceTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/offence")
public class OffenceTypeController {

    private final OffenceTypeService offenceTypeService;

    public OffenceTypeController(OffenceTypeService offenceTypeService) {
        this.offenceTypeService = offenceTypeService;
    }

    @GetMapping
    public List<OffenceType> getAll() {
        return offenceTypeService.getAll();
    }
}
