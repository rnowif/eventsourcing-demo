package com.rnowif.events.infra;

import com.rnowif.events.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Collections.emptyList;

public class SimpleEventBus implements EventPublisher, EventRegistrar {

    private final Map<Class<? extends Event>, List<Consumer<Event>>> handlers;
    private final EventStore eventStore;

    public SimpleEventBus() {
        this(new NoopEventStore());
    }

    public SimpleEventBus(EventStore eventStore) {
        this.eventStore = eventStore;
        handlers = new HashMap<>();
    }

    @Override
    public <T extends Event> void register(Class<T> eventClass, Consumer<T> handler) {
        handlers.putIfAbsent(eventClass, new ArrayList<>());
        handlers.get(eventClass).add((Consumer<Event>) handler);
    }

    @Override
    public void publish(Event event) {
        eventStore.save(event);
        handlers.getOrDefault(event.getClass(), emptyList())
                .forEach(handler -> handler.accept(event));
    }

}
