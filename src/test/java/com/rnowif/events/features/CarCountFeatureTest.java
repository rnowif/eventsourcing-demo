package com.rnowif.events.features;

import com.rnowif.events.domain.parkinglot.CarCountHandler;
import com.rnowif.events.domain.parkinglot.ParkingLot;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;
import com.rnowif.events.infra.SimpleEventBus;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CarCountFeatureTest {
    
    @Test
    public void should_count_cars_when_entering_and_exiting() {
        SimpleEventBus eventBus = new SimpleEventBus();
        CarCountHandler handler = new CarCountHandler();
        eventBus.register(CarEntered.class, handler::apply);
        eventBus.register(CarExited.class, handler::apply);
        ParkingLot parkingLot = new ParkingLot(eventBus);

        for (int i = 0; i < 5; i++) {
            parkingLot.enterCar();
        }

        for (int i = 0; i < 2; i++) {
            parkingLot.exitCar();
        }

        assertThat(handler.getNbCars(), is(3));

    }
}
