package com.rnowif.events.domain.parkinglot.events;

import com.rnowif.events.domain.Event;

import java.time.LocalTime;

public class CarEntered implements Event {
    private final LocalTime time;

    public CarEntered(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarEntered that = (CarEntered) o;

        return time.equals(that.time);

    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
}
