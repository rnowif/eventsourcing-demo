package com.rnowif.events.domain.parkinglot;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.rnowif.events.domain.Event;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.rnowif.events.domain.parkinglot.ParkingLotId.of;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(JUnitQuickcheck.class)
public class ParkingLotTest {

    private List<Object> events;

    @Before
    public void setUp() throws Exception {
        events = new ArrayList<>();
    }

    private ParkingLot newParkingLot() {
        return new ParkingLot(of(1L), Integer.MAX_VALUE, events::add);
    }

    @Property
    public void should_emit_car_entered_event_when_a_car_enters(@InRange (min = "0", max = "23") int hour) {
        ParkingLot parkingLot = newParkingLot();
        LocalTime time = LocalTime.of(hour, 0);

        parkingLot.enterCar(time);

        assertThat(events.size(), is(1));
        assertThat(((CarEntered) events.get(0)).getEntranceTime(), is(time));
    }

    @Test
    public void should_emit_car_exited_event_when_a_car_exits() {
        ParkingLot parkingLot = newParkingLot();
        parkingLot.exitCar();

        assertThat(events.size(), is(1));
        assertThat(events.get(0), instanceOf(CarExited.class));
    }

    @Property
    public void should_throw_exception_when_capacity_exceeded(@InRange(min = "1", max = "100") int capacity) {
        ParkingLot parkingLot = new ParkingLot(of(1L), capacity, events::add, generateEntrances(capacity));

        try {
            parkingLot.enterCar(LocalTime.now());
            fail("Should have thrown exception when capacity exceeded");
        } catch (CapacityExceeded e) {
            // Expected behavior
        }
    }

    private List<Event> generateEntrances(int count) {
        return Stream.generate(() -> new CarEntered(of(1L), Instant.now(), LocalTime.now()))
                .limit(count).collect(toList());
    }

}