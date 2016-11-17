package com.rnowif.events.infra;

import com.rnowif.events.domain.EventPublisher;
import com.rnowif.events.domain.EventRegistrar;

import java.util.function.Consumer;

public class SimpleEventBus implements EventPublisher, EventRegistrar {
    @Override
    public <T> void register(Class<T> eventClass, Consumer<T> handler) {

    }

    @Override
    public <T> void publish(T event) {

    }
}
