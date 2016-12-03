package com.rnowif.events.domain.parkinglot;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.rnowif.events.domain.parkinglot.events.CarEntered;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitQuickcheck.class)
public class FlowPerHourHandlerTest {
    
    @Test
    public void should_return_zero_when_no_car() {
        FlowPerHourHandler handler = new FlowPerHourHandler();
        assertThat(handler.getFor(0), is(0));
    }
    
    @Property
    public void should_add_car_when_car_enters(
            @InRange(min = "0", max = "23") int hour,
            @InRange(min = "0", max = "100") int nbCars
    ) {
        FlowPerHourHandler handler = new FlowPerHourHandler();

        for (int i = 0; i < nbCars; i++) {
            handler.apply(new CarEntered(LocalTime.of(hour, 0)));
        }

        assertThat(handler.getFor(hour), is(nbCars));
    }

}