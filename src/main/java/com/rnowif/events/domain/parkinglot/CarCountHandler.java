package com.rnowif.events.domain.parkinglot;

import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;

import java.util.concurrent.atomic.AtomicInteger;

public class CarCountHandler {

    private AtomicInteger nbCars = new AtomicInteger(0);

    public void apply(CarEntered carEntered) {
        nbCars.incrementAndGet();
    }

    public synchronized void apply(CarExited carExited) {
        if (getNbCars() > 0) {
            nbCars.decrementAndGet();
        }
    }

    public int getNbCars() {
        return nbCars.get();
    }
}
