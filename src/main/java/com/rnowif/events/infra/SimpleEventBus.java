package com.rnowif.events.infra;

import com.rnowif.events.domain.EventPublisher;
import com.rnowif.events.domain.EventRegistrar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Collections.emptyList;

public class SimpleEventBus implements EventPublisher, EventRegistrar {

    private final Map<Class<?>, List<Consumer<?>>> handlers;

    public SimpleEventBus() {
        handlers = new HashMap<>();
    }

    @Override
    public <T> void register(Class<T> eventClass, Consumer<T> handler) {
        handlers.putIfAbsent(eventClass, new ArrayList<>());
        handlers.get(eventClass).add(handler);
    }

    @Override
    public <T> void publish(T event) {
        handlers.getOrDefault(event.getClass(), emptyList())
                .forEach(handler -> ((Consumer<T>) handler).accept(event));
    }
}
