package com.rnowif.events.domain.parkinglot.events;

import com.rnowif.events.domain.EntityId;
import com.rnowif.events.domain.Event;
import com.rnowif.events.domain.parkinglot.ParkingLotId;

import java.time.Instant;

public class ParkingLotCreated implements Event {

    private final ParkingLotId parkingLotId;
    private final Instant timestamp;
    private final int capacity;

    public ParkingLotCreated(ParkingLotId parkingLotId, Instant timestamp, int capacity) {
        this.parkingLotId = parkingLotId;
        this.timestamp = timestamp;
        this.capacity = capacity;
    }

    @Override
    public EntityId getEntityId() {
        return parkingLotId;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    public int getCapacity() {
        return capacity;
    }
}
