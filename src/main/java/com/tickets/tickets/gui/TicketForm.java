package com.tickets.tickets.gui;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.time.LocalDate;

@SpringComponent
@UIScope
public class TicketForm extends FormLayout {

    private final TextField pesel = new TextField("PESEL");
    private DatePicker date = new DatePicker("Select a date:");

    public TicketForm() {
        add(date, pesel);
        date.setMax(LocalDate.now());
    }

    public LocalDate getDate() {
        return date.getValue();
    }

    public String getPesel() {
        return pesel.getValue();
    }
}
