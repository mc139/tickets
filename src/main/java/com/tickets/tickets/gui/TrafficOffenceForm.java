package com.tickets.tickets.gui;

import com.tickets.tickets.constant.Constants;
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
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
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

        crud = getTrafficOffenceCrud();
        grid.setItems(this.trafficOffenceService.findAll());
        configureGrid();
        setupDataProvider();
        crud.setGrid(grid);
        add(crud);
    }

    private Crud<TrafficOffence> getTrafficOffenceCrud() {
        final Crud<TrafficOffence> crud;
        crud = new Crud<>(
                TrafficOffence.class,
                createEditor()
        );
        return crud;
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
        cofigureDelete();
        configureSave();
    }

    private void configureSave() {
        crud.addSaveListener(saveEvent ->{
            log.info("User created custom traffic offence : " + saveEvent.getItem().getOffenceType());
            trafficOffenceService.save(saveEvent.getItem());
                }

        );
    }

    private void cofigureDelete() {
        crud.addDeleteListener(deleteEvent -> {
            log.info("User deleted traffic offence : " + deleteEvent.getItem().getOffenceType());
                    trafficOffenceDataProvider.delete(deleteEvent.getItem());
                }
        );
    }

    private CrudEditor<TrafficOffence> createEditor() {
        TextField numberOfPoints = new TextField("number of points");
        TextField ticketValue = new TextField("ticket value");
        TextField offenceType = new TextField("type of offence");
        FormLayout form = new FormLayout(offenceType, numberOfPoints, ticketValue);
        Binder<TrafficOffence> binder = new Binder<>(TrafficOffence.class);
        configureBinder(numberOfPoints, ticketValue, offenceType, binder);
        return new BinderCrudEditor<>(binder, form);
    }

    private void configureBinder(TextField numberOfPoints, TextField ticketValue, TextField offenceType, Binder<TrafficOffence> binder) {
        int maxTicketValue = 5000;
        int maxPointPerTicket = 15;
        binder.forField(offenceType)
                .asRequired(Constants.EMPTY_FIELD_MESSAGE)
                .withValidator(value -> value.length() >= 3, Constants.MIN_LENGTH_MESSAGE)
                .withValidator(value -> value.length() < 250, Constants.MAX_LENGTH_MESSAGE)
                .bind(TrafficOffence::getOffenceType, TrafficOffence::setOffenceType);
        binder.forField(numberOfPoints)
                .asRequired(Constants.EMPTY_FIELD_MESSAGE)
                .withConverter(new StringToIntegerConverter(Constants.NOT_A_NUMBER_MESSAGE))
                .withValidator(value -> value >= 0, Constants.MIN_NUMBER_OF_POINTS_MESSAGE)
                .withValidator(value -> value <= maxPointPerTicket, Constants.MAX_NUMBER_OF_POINTS_MESSAGE + maxPointPerTicket)
                .bind(TrafficOffence::getNumberOfPoints, TrafficOffence::setNumberOfPoints);

        binder.forField(ticketValue)
                .asRequired(Constants.EMPTY_FIELD_MESSAGE)
                .withConverter(new StringToIntegerConverter(Constants.NOT_A_NUMBER_MESSAGE))
                .withValidator(value -> value >= 0, Constants.TICKET_VALUE_MIN_MESSAGE)
                .withValidator(value -> value <= maxTicketValue, Constants.TICKET_VALUE_MAX_MESSAGE + maxTicketValue)
                .bind(TrafficOffence::getTicketValue, TrafficOffence::setTicketValue);
    }

    public Grid<TrafficOffence> getGrid() {
        return grid;
    }

    public List<TrafficOffence> getSelectedItems() {
        return new ArrayList<>(grid.asMultiSelect().getSelectedItems());
    }

}

