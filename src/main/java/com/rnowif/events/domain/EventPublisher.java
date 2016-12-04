package com.rnowif.events.domain;

@FunctionalInterface
public interface EventPublisher {

    void publish(Event event);
}
