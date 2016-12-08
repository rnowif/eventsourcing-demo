package com.rnowif.events.domain.parkinglot;

import com.rnowif.events.domain.Event;
import com.rnowif.events.domain.EventPublisher;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;

import java.time.Instant;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static java.time.Instant.now;

public class ParkingLot {

    private final int capacity;
    private final EventPublisher eventPublisher;
    private final Map<Class<? extends Event>, Consumer<Event>> consumers;
    private final ParkingLotId id;

    private final AtomicInteger nbCars = new AtomicInteger(0);

    public ParkingLot(ParkingLotId id, int capacity, EventPublisher eventPublisher) {
        this.id = id;
        this.capacity = capacity;
        this.eventPublisher = eventPublisher;
        this.consumers = new HashMap<>();
        this.consumers.put(CarEntered.class, e -> this.apply((CarEntered) e));
    }

    public ParkingLot(ParkingLotId id, int capacity, EventPublisher eventPublisher, List<Event> events) {
        this(id, capacity, eventPublisher);
        events.forEach(e -> consumers.getOrDefault(e.getClass(), o -> {}).accept(e));
    }

    private void apply(CarEntered event) {
        if (nbCars.get() >= capacity) {
            throw new CapacityExceeded();
        }

        nbCars.incrementAndGet();
    }

    public synchronized void enterCar(LocalTime entranceTime) {
        CarEntered event = new CarEntered(id, now(), entranceTime);
        apply(event);
        eventPublisher.publish(event);
    }

    public synchronized void exitCar() {
        if (nbCars.get() > 0) {
            nbCars.decrementAndGet();
        }

        eventPublisher.publish(new CarExited(id, now()));
    }
}
