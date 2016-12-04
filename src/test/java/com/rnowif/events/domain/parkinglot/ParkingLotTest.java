package com.rnowif.events.domain.parkinglot;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(JUnitQuickcheck.class)
public class ParkingLotTest {

    private List<Object> events;
    private ParkingLot parkingLot;

    @Before
    public void setUp() throws Exception {
        events = new ArrayList<>();
        parkingLot = new ParkingLot(Integer.MAX_VALUE, events::add);
    }

    @Property
    public void should_emit_car_entered_event_when_a_car_enters(@InRange (min = "0", max = "23") int hour) {
        LocalTime time = LocalTime.of(hour, 0);

        parkingLot.enterCar(time);

        assertThat(events.size(), is(1));
        assertThat(events, hasItems(new CarEntered(time)));
    }

    @Test
    public void should_emit_car_exited_event_when_a_car_exits() {
        parkingLot.exitCar();

        assertThat(events.size(), is(1));
        assertThat(events.get(0), instanceOf(CarExited.class));
    }

    @Property
    public void should_throw_exception_when_capacity_exceeded(@InRange(min = "1", max = "100") int capacity) {
        parkingLot = new ParkingLot(capacity, events::add);
        for (int i = 0; i < capacity; i++) {
            parkingLot.enterCar(LocalTime.now());
        }

        try {
            parkingLot.enterCar(LocalTime.now());
            fail("Should have thrown exception when capacity exceeded");
        } catch (CapacityExceeded e) {
            // Expected behavior
        }
    }

}