package com.rnowif.events.domain.parkinglot;

import com.rnowif.events.domain.Event;
import com.rnowif.events.domain.EventPublisher;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;
import com.rnowif.events.domain.parkinglot.events.ParkingLotCreated;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static java.time.Instant.now;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class ParkingLot {

    private final EventPublisher eventPublisher;

    private ParkingLotId id;
    private int capacity;
    private final AtomicInteger nbCars = new AtomicInteger(0);

    private ParkingLot(EventPublisher eventPublisher, List<Event> events) {
        this.eventPublisher = eventPublisher;
        Map<Class<? extends Event>, Consumer<Event>> consumers = buildConsumers();
        events.forEach(e -> consumers.getOrDefault(e.getClass(), o -> {}).accept(e));
    }

    private Map<Class<? extends Event>, Consumer<Event>> buildConsumers() {
        Map<Class<? extends Event>, Consumer<Event>> consumers = new HashMap<>();
        consumers.put(CarEntered.class, e -> this.apply((CarEntered) e));
        consumers.put(CarExited.class, e -> this.apply((CarExited) e));
        consumers.put(ParkingLotCreated.class, e -> this.apply((ParkingLotCreated) e));
        return consumers;
    }

    private void apply(ParkingLotCreated event) {
        this.id = (ParkingLotId) event.getEntityId();
        this.capacity = event.getCapacity();
    }

    private void apply(CarEntered event) {
        nbCars.incrementAndGet();
    }

    private void apply(CarExited event) {
        if (nbCars.get() > 0) {
            nbCars.decrementAndGet();
        }
    }

    public synchronized void enterCar(LocalTime entranceTime) {
        if (nbCars.get() >= capacity) {
            throw new CapacityExceeded();
        }

        CarEntered event = new CarEntered(id, now(), entranceTime);
        apply(event);
        eventPublisher.publish(event);
    }

    public synchronized void exitCar() {
        CarExited event = new CarExited(id, now());
        apply(event);
        eventPublisher.publish(event);
    }

    public static ParkingLot create(ParkingLotId id, int capacity, EventPublisher eventPublisher) {
        ParkingLotCreated creationEvent = new ParkingLotCreated(id, now(), capacity);
        eventPublisher.publish(creationEvent);
        return new ParkingLot(eventPublisher, singletonList(creationEvent));
    }

    public static ParkingLot fromEvents(EventPublisher eventPublisher, List<Event> events) {
        return new ParkingLot(eventPublisher, events);
    }
}
