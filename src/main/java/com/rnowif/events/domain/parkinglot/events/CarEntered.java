package com.rnowif.events.domain.parkinglot.events;

import com.rnowif.events.domain.EntityId;
import com.rnowif.events.domain.Event;
import com.rnowif.events.domain.parkinglot.ParkingLotId;

import java.time.Instant;
import java.time.LocalTime;

public class CarEntered implements Event {

    private final ParkingLotId parkingLotId;
    private final Instant eventTime;
    private final LocalTime entranceTime;

    public CarEntered(ParkingLotId parkingLotId, Instant eventTime, LocalTime entranceTime) {
        this.parkingLotId = parkingLotId;
        this.eventTime = eventTime;
        this.entranceTime = entranceTime;
    }

    public LocalTime getEntranceTime() {
        return entranceTime;
    }

    @Override
    public EntityId getEntityId() {
        return parkingLotId;
    }

    @Override
    public Instant getTimestamp() {
        return eventTime;
    }
}
