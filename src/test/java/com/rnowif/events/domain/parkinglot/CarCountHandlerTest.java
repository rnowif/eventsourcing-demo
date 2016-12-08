package com.rnowif.events.domain.parkinglot;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import com.rnowif.events.domain.parkinglot.events.CarExited;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.time.LocalTime;

import static com.rnowif.events.domain.parkinglot.ParkingLotId.of;
import static java.util.stream.IntStream.range;
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

        range(0, nbCarsIn).forEach(i -> handler.apply(new CarEntered(of(1L), Instant.now(), LocalTime.now())));
        range(0, nbCarsOut).forEach(i -> handler.apply(new CarExited(of(1L), Instant.now())));

        assertThat(handler.getNbCars(), is(nbCarsIn - nbCarsOut));
    }
    
    @Test
    public void should_have_zero_cars_when_no_car_and_one_car_exits() {
        CarCountHandler handler = new CarCountHandler();
        handler.apply(new CarExited(of(1L), Instant.now()));
        assertThat(handler.getNbCars(), is(0));
    }

}