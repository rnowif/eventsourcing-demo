package com.rnowif.events.features;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.rnowif.events.domain.parkinglot.CarCountHandler;
import com.rnowif.events.domain.parkinglot.ParkingLot;
import com.rnowif.events.domain.parkinglot.ParkingLotId;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;
import com.rnowif.events.infra.SimpleEventBus;
import org.junit.runner.RunWith;

import java.time.LocalTime;

import static java.util.stream.IntStream.range;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;

@RunWith(JUnitQuickcheck.class)
public class CarCountFeatureTest {
    
    @Property
    public void should_count_cars_when_entering_and_exiting(
            @InRange(min = "0", max = "100") int nbCarsIn,
            @InRange(min = "0", max = "100") int nbCarsOut
    ) {
        assumeTrue(nbCarsIn >= nbCarsOut);
        SimpleEventBus eventBus = eventBus();
        CarCountHandler handler = carCountHandler(eventBus);
        ParkingLot parkingLot = parkingLot(eventBus);

        range(0, nbCarsIn).forEach(i -> parkingLot.enterCar(LocalTime.now()));
        range(0, nbCarsOut).forEach(i -> parkingLot.exitCar());

        assertThat(handler.getNbCars(), is(nbCarsIn - nbCarsOut));
    }

    private SimpleEventBus eventBus() {
        return new SimpleEventBus();
    }

    private CarCountHandler carCountHandler(SimpleEventBus eventBus) {
        CarCountHandler handler = new CarCountHandler();
        eventBus.register(CarEntered.class, handler::apply);
        eventBus.register(CarExited.class, handler::apply);
        return handler;
    }

    private ParkingLot parkingLot(SimpleEventBus eventBus) {
        return new ParkingLot(ParkingLotId.of(1L), Integer.MAX_VALUE, eventBus);
    }
}
