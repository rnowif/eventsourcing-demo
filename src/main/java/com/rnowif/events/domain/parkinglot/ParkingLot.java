package com.rnowif.events.domain.parkinglot;

import com.rnowif.events.domain.EventPublisher;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;

public class ParkingLot {
    private final EventPublisher eventPublisher;

    public ParkingLot(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void enterCar() {
        eventPublisher.publish(new CarEntered());
    }

    public void exitCar() {
        eventPublisher.publish(new CarExited());
    }
}
