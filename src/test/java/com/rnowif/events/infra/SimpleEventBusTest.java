package com.rnowif.events.infra;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleEventBusTest {

    private class Event {}

    @Test
    public void should_not_crash_when_no_callback_registered() {
        SimpleEventBus bus = new SimpleEventBus();

        bus.publish(new Event());
    }

    @Test
    public void should_call_registered_callback_whenever_an_event_is_published() {
        List<Event> events = new ArrayList<>();
        SimpleEventBus bus = new SimpleEventBus();

        bus.register(Event.class, events::add);
        bus.publish(new Event());
        bus.publish(new Event());

        assertThat(events.size(), is(2));
    }
    
    @Test
    public void should_call_all_registered_callbacks_when_an_event_is_published() {
        List<Event> firstEvents = new ArrayList<>();
        List<Event> secondEvents = new ArrayList<>();
        SimpleEventBus bus = new SimpleEventBus();

        bus.register(Event.class, firstEvents::add);
        bus.register(Event.class, secondEvents::add);
        bus.publish(new Event());

        assertThat(firstEvents.size(), is(1));
        assertThat(secondEvents.size(), is(1));
    }
}