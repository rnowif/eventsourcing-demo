package com.rnowif.events.domain;

@FunctionalInterface
public interface EventPublisher {

    <T> void publish(T event);
}
