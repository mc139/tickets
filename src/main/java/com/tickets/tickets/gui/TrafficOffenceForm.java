package com.tickets.tickets.gui;

import com.tickets.tickets.model.TrafficOffence;
import com.tickets.tickets.service.TrafficOffenceService;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
public class TrafficOffenceForm extends Div {
    private final Crud<TrafficOffence> crud;
    private final Grid<TrafficOffence> grid = new Grid<>();
    private final TrafficOffenceDataProvider trafficOffenceDataProvider;
    private final TrafficOffenceService trafficOffenceService;

    public TrafficOffenceForm(TrafficOffenceDataProvider trafficOffenceDataProvider, TrafficOffenceService trafficOffenceService) {
        this.trafficOffenceDataProvider = trafficOffenceDataProvider;
        this.trafficOffenceService = trafficOffenceService;

        crud = new Crud<>(
                TrafficOffence.class,
                createEditor()
        );
        grid.setItems(this.trafficOffenceService.findAll());
        configureGrid();
        setupDataProvider();
        crud.setGrid(grid);

        add(crud);
    }

    private void newItem() {
        System.out.println();
    }

    private void configureGrid() {
        grid.addClassNames("Offences");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(TrafficOffence::getOffenceType).setHeader("OFFENCE TYPE");
        grid.addColumn(TrafficOffence::getTicketValue).setHeader("TICKET VALUE");
        grid.addColumn(TrafficOffence::getNumberOfPoints).setHeader("NUMBER OF POINTS");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.asMultiSelect();
    }

    private void setupDataProvider() {
        crud.setDataProvider(trafficOffenceDataProvider);
        crud.addDeleteListener(deleteEvent ->
                trafficOffenceDataProvider.delete(deleteEvent.getItem())
        );
        crud.addSaveListener(saveEvent ->
                trafficOffenceService.save(saveEvent.getItem())
        );
    }

    private CrudEditor<TrafficOffence> createEditor() {
        TextField numberOfPoints = new TextField("number of points");
        TextField ticketValue = new TextField("ticket value");
        TextField offenceType = new TextField("type of offence");
        FormLayout form = new FormLayout(offenceType, numberOfPoints, ticketValue);
        Binder<TrafficOffence> binder = new Binder<>(TrafficOffence.class);
        binder.forField(offenceType)
                .withValidator(value -> value.length() >= 3,"Type of offence must be at least 3 characters")
                .withValidator(value -> value.length() < 250,"Type of offence can't be longer then 250 characters")
                .bind(TrafficOffence::getOffenceType, TrafficOffence::setOffenceType);
        binder.forField(numberOfPoints)
                .withConverter(new StringToIntegerConverter("It must be a number"))
                .withValidator(value -> value >= 0, "Number of points can't be lower then 0")
                .withValidator(value -> value >= 15, "Number of points must be lower then 15 ")
                .bind(TrafficOffence::getNumberOfPoints, TrafficOffence::setNumberOfPoints);
        binder.forField(ticketValue)
                .withConverter(new StringToIntegerConverter("It must be a number"))
                .withValidator(value -> value >= 0, "Ticket Value must be greater then 0")
                .withValidator(value -> value <= 15000, "Ticket Value must be lower then 1500")
                .bind(TrafficOffence::getTicketValue, TrafficOffence::setTicketValue);
        return new BinderCrudEditor<>(binder, form);
    }

    public Grid<TrafficOffence> getGrid() {
        return grid;
    }

    public List<TrafficOffence> getSelectedItems() {
        return new ArrayList<>(grid.asMultiSelect().getSelectedItems());
    }

}

