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
    private Crud<TrafficOffence> crud;
    private Grid<TrafficOffence> grid = new Grid<>();

    public TrafficOffenceForm(TrafficOffenceService trafficOffenceService) {

        crud = new Crud<>(
                TrafficOffence.class,
                createEditor()
        );
        grid.setItems(trafficOffenceService.findAll());
        configureGrid();
        crud.setGrid(grid);

        add(crud);
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

    private CrudEditor<TrafficOffence> createEditor() {
        TextField numberOfPoints = new TextField("number of points");
        TextField ticketValue = new TextField("ticket Value");
        FormLayout form = new FormLayout(numberOfPoints, ticketValue);
        Binder<TrafficOffence> binder = new Binder<>(TrafficOffence.class);
        binder.forField(numberOfPoints)
                .withConverter(new StringToIntegerConverter("It must be a number"))
                .bind(TrafficOffence::getNumberOfPoints, TrafficOffence::setNumberOfPoints);
        binder.forField(ticketValue)
                .withConverter(new StringToIntegerConverter("It must be a number"))
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
