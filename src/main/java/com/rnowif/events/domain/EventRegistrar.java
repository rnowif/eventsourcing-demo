package com.rnowif.events.domain;

import java.util.function.Consumer;

@FunctionalInterface
public interface EventRegistrar {
    <T extends Event> void register(Class<T> eventClass, Consumer<T> handler);
}
