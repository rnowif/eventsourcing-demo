package com.rnowif.events.infra;

import com.rnowif.events.domain.EntityId;
import com.rnowif.events.domain.Event;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleEventBusTest {

    private class SimpleEvent implements Event {

        private class Id implements EntityId {}

        @Override
        public EntityId getEntityId() {
            return new Id();
        }

        @Override
        public Instant getTimestamp() {
            return Instant.now();
        }
    }

    @Test
    public void should_not_crash_when_no_callback_registered() {
        SimpleEventBus bus = new SimpleEventBus();

        bus.publish(new SimpleEvent());
    }

    @Test
    public void should_call_registered_callback_whenever_an_event_is_published() {
        List<Event> events = new ArrayList<>();
        SimpleEventBus bus = new SimpleEventBus();

        bus.register(SimpleEvent.class, events::add);
        bus.publish(new SimpleEvent());
        bus.publish(new SimpleEvent());

        assertThat(events.size(), is(2));
    }
    
    @Test
    public void should_call_all_registered_callbacks_when_an_event_is_published() {
        List<Event> firstEvents = new ArrayList<>();
        List<Event> secondEvents = new ArrayList<>();
        SimpleEventBus bus = new SimpleEventBus();

        bus.register(SimpleEvent.class, firstEvents::add);
        bus.register(SimpleEvent.class, secondEvents::add);
        bus.publish(new SimpleEvent());

        assertThat(firstEvents.size(), is(1));
        assertThat(secondEvents.size(), is(1));
    }
}