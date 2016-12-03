package com.rnowif.events.domain.parkinglot;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;

@RunWith(JUnitQuickcheck.class)
public class CarCountHandlerTest {
    
    @Test
    public void should_have_zero_cars_when_no_events() {
        assertThat(new CarCountHandler().getNbCars(), is(0));
    }

    @Property
    public void should_count_cars_when_cars_entered_and_exited(
            @InRange(min = "0", max = "100") int nbCarsIn,
            @InRange(min = "0", max = "100") int nbCarsOut
    ) {
        assumeTrue(nbCarsIn >= nbCarsOut);
        CarCountHandler handler = new CarCountHandler();

        enterCars(nbCarsIn, handler);
        exitCars(nbCarsOut, handler);

        assertThat(handler.getNbCars(), is(nbCarsIn - nbCarsOut));
    }
    
    @Test
    public void should_have_zero_cars_when_no_car_and_one_car_exits() {
        CarCountHandler handler = new CarCountHandler();
        exitCars(1, handler);
        assertThat(handler.getNbCars(), is(0));
    }

    private void exitCars(int nbCarsOut, CarCountHandler handler) {
        for (int i = 0; i < nbCarsOut; i++) {
            handler.apply(new CarExited());
        }
    }

    private void enterCars(int nbCars, CarCountHandler handler) {
        for (int i = 0; i < nbCars; i++) {
            handler.apply(new CarEntered());
        }
    }

}