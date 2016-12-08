package com.rnowif.events.domain.parkinglot;

import com.rnowif.events.domain.parkinglot.events.CarEntered;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlowPerHourHandler {

    private final Map<Integer, Integer> countByHour = new ConcurrentHashMap<>();

    public void apply(CarEntered event) {
        countByHour.putIfAbsent(event.getEntranceTime().getHour(), 0);
        countByHour.compute(event.getEntranceTime().getHour(), (hour, count) -> count + 1);
    }

    public int getFor(int hour) {
        return countByHour.getOrDefault(hour, 0);
    }
}
