package com.rnowif.events.domain.parkinglot.events;

import com.rnowif.events.domain.EntityId;
import com.rnowif.events.domain.Event;
import com.rnowif.events.domain.parkinglot.ParkingLotId;

import java.time.Instant;

public class CarExited implements Event {

    private final ParkingLotId parkingLotId;
    private final Instant eventTime;

    public CarExited(ParkingLotId parkingLotId, Instant eventTime) {
        this.parkingLotId = parkingLotId;
        this.eventTime = eventTime;
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
