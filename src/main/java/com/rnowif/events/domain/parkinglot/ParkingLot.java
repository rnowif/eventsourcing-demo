package com.rnowif.events.domain.parkinglot;

import com.rnowif.events.domain.EventPublisher;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLot {

    private final int capacity;
    private final EventPublisher eventPublisher;
    private AtomicInteger nbCars = new AtomicInteger(0);

    public ParkingLot(int capacity, EventPublisher eventPublisher) {
        this.capacity = capacity;
        this.eventPublisher = eventPublisher;
    }

    public synchronized void enterCar(LocalTime entranceTime) {
        if (nbCars.get() >= capacity) {
            throw new CapacityExceeded();
        }

        nbCars.incrementAndGet();
        eventPublisher.publish(new CarEntered(entranceTime));
    }

    public synchronized void exitCar() {
        if (nbCars.get() > 0) {
            nbCars.decrementAndGet();
        }

        eventPublisher.publish(new CarExited());
    }
}
