package com.rnowif.events.domain.parkinglot;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.rnowif.events.domain.Event;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;
import com.rnowif.events.domain.parkinglot.events.ParkingLotCreated;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.rnowif.events.domain.parkinglot.ParkingLotId.of;
import static java.time.Instant.now;
import static java.util.Arrays.asList;
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
    
    @Property
    public void should_emit_event_on_creation(@InRange(min = "0", max = "100") int capacity) {
        ParkingLot.create(of(1L), capacity, events::add);

        assertThat(events.size(), is(1));
        assertThat(((ParkingLotCreated) events.get(0)).getCapacity(), is(capacity));
    }

    private ParkingLot newParkingLot() {
        return ParkingLot.fromEvents(events::add, asList(new ParkingLotCreated(of(1L), now(), Integer.MAX_VALUE)));
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
        List<Event> events = Stream.concat(
                Stream.of(new ParkingLotCreated(of(1L), now(), capacity)),
                generateEntrances(capacity).stream()
        ).collect(toList());

        ParkingLot parkingLot = ParkingLot.fromEvents(e -> {}, events);

        try {
            parkingLot.enterCar(LocalTime.now());
            fail("Should have thrown exception when capacity exceeded");
        } catch (CapacityExceeded e) {
            // Expected behavior
        }
    }

    private List<Event> generateEntrances(int count) {
        return Stream.generate(() -> new CarEntered(of(1L), now(), LocalTime.now()))
                .limit(count).collect(toList());
    }

}