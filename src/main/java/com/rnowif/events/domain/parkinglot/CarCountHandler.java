package com.rnowif.events.domain.parkinglot;

import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;

public class CarCountHandler {
    public CarCountHandler() {

    }

    public void apply(CarEntered carEntered) {

    }

    public void apply(CarExited carExited) {
        
    }

    public int getNbCars() {
        return 0;
    }
}
