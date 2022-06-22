package com.tickets.tickets.gui;

import com.tickets.tickets.model.TrafficOffence;
import com.tickets.tickets.service.TrafficOffenceService;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;

@UIScope
@SpringComponent
public class TrafficOffenceDataProvider extends AbstractBackEndDataProvider<TrafficOffence, CrudFilter> {


    final List<TrafficOffence> DATABASE;

    private Consumer<Long> sizeChangeListener;

    public TrafficOffenceDataProvider(TrafficOffenceService trafficOffenceService) {
        this.DATABASE = new ArrayList<>(trafficOffenceService.findAll());
    }

    @Override
    protected Stream<TrafficOffence> fetchFromBackEnd(Query<TrafficOffence, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<TrafficOffence> stream = DATABASE.stream();

        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<TrafficOffence, CrudFilter> query) {
        // For RDBMS just execute a SELECT COUNT(*) ... WHERE query
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<TrafficOffence> predicate(CrudFilter filter) {
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<TrafficOffence>) trafficOffence -> {
                    try {
                        Object value = valueOf(constraint.getKey(), trafficOffence);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .reduce(Predicate::and)
                .orElse(e -> true);
    }

    private static Comparator<TrafficOffence> comparator(CrudFilter filter) {
        // For RDBMS just generate an ORDER BY clause
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<TrafficOffence> comparator = Comparator.comparing(trafficOffence ->
                                (Comparable) valueOf(sortClause.getKey(), trafficOffence)
                        );

                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }

                        return comparator;

                    } catch (Exception ex) {
                        return (Comparator<TrafficOffence>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, TrafficOffence person) {
        try {
            Field field = TrafficOffence.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(person);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void persist(TrafficOffence item) {
        if (item.getId() == null) {
            item.setId(DATABASE
                    .stream()
                    .map(TrafficOffence::getId)
                    .max(naturalOrder())
                    .orElse(0L) + 1);
        }

        final Optional<TrafficOffence> existingItem = find(item.getId());
        if (existingItem.isPresent()) {
            int position = DATABASE.indexOf(existingItem.get());
            DATABASE.remove(existingItem.get());
            DATABASE.add(position, item);
        } else {
            DATABASE.add(item);
        }
    }

    Optional<TrafficOffence> find(Long id) {
        return DATABASE
                .stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    void delete(TrafficOffence item) {
        DATABASE.removeIf(entity -> entity.getId().equals(item.getId()));
    }
}
