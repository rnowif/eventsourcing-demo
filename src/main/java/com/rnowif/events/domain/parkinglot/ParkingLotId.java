package com.rnowif.events.domain.parkinglot;

import com.rnowif.events.domain.EntityId;

import java.util.Random;

public class ParkingLotId implements EntityId {

    private final long id;

    private ParkingLotId(long id) {
        this.id = id;
    }

    public static ParkingLotId of(long id) {
        return new ParkingLotId(id);
    }

    public static ParkingLotId generate() {
        Random random = new Random(System.currentTimeMillis());
        return new ParkingLotId(random.nextLong());
    }
}
