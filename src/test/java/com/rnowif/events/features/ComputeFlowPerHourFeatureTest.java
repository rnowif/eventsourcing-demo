package com.rnowif.events.features;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.rnowif.events.domain.parkinglot.ParkingLot;
import com.rnowif.events.domain.parkinglot.ParkingLotId;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.FlowPerHourHandler;
import com.rnowif.events.infra.SimpleEventBus;
import org.junit.runner.RunWith;

import java.time.LocalTime;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitQuickcheck.class)
public class ComputeFlowPerHourFeatureTest {
    
    @Property
    public void should_compute_filling_rate_for_every_hours(
            @InRange (min = "0", max = "100") int nbCars,
            @InRange (min = "0", max = "23") int hour
    ) {
        SimpleEventBus eventBus = eventBus();
        FlowPerHourHandler handler = flowPerHourHandler(eventBus);
        ParkingLot parkingLot = parkingLot(eventBus);

        IntStream.range(0, nbCars).forEach(i -> parkingLot.enterCar(LocalTime.of(hour, 0)));

        assertThat(handler.getFor(hour), is(nbCars));
        assertThat(handler.getFor(hour + 1 % 24), is(0));
    }

    private SimpleEventBus eventBus() {
        return new SimpleEventBus();
    }

    private FlowPerHourHandler flowPerHourHandler(SimpleEventBus eventBus) {
        FlowPerHourHandler handler = new FlowPerHourHandler();
        eventBus.register(CarEntered.class, handler::apply);
        return handler;
    }

    private ParkingLot parkingLot(SimpleEventBus eventBus) {
        return ParkingLot.create(ParkingLotId.of(1L), Integer.MAX_VALUE, eventBus);
    }
}
