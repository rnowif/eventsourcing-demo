package com.rnowif.events.features;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.rnowif.events.domain.parkinglot.CarCountHandler;
import com.rnowif.events.domain.parkinglot.ParkingLot;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;
import com.rnowif.events.infra.SimpleEventBus;
import org.junit.runner.RunWith;

import java.time.LocalTime;

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
        SimpleEventBus eventBus = new SimpleEventBus();
        CarCountHandler handler = new CarCountHandler();
        eventBus.register(CarEntered.class, handler::apply);
        eventBus.register(CarExited.class, handler::apply);
        ParkingLot parkingLot = new ParkingLot(eventBus);

        for (int i = 0; i < nbCarsIn; i++) {
            parkingLot.enterCar(LocalTime.now());
        }

        for (int i = 0; i < nbCarsOut; i++) {
            parkingLot.exitCar();
        }

        assertThat(handler.getNbCars(), is(nbCarsIn - nbCarsOut));

    }
}
