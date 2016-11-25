package com.rnowif.events.domain.parkinglot;

import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class ParkingLotTest {

    private List<Object> events;
    private ParkingLot parkingLot;

    @Before
    public void setUp() throws Exception {
        events = new ArrayList<>();
        parkingLot = new ParkingLot(events::add);
    }

    @Test
    public void should_emit_car_entered_event_when_a_car_enters() {
        parkingLot.enterCar();

        assertThat(events.size(), is(1));
        assertThat(events.get(0), instanceOf(CarEntered.class));
    }

    @Test
    public void should_emit_car_exited_event_when_a_car_exits() {
        parkingLot.exitCar();

        assertThat(events.size(), is(1));
        assertThat(events.get(0), instanceOf(CarExited.class));
    }

}