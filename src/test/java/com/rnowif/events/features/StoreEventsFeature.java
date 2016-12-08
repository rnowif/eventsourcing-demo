package com.rnowif.events.features;

import com.rnowif.events.domain.Event;
import com.rnowif.events.domain.EventStore;
import com.rnowif.events.fixtures.FakeEvent;
import com.rnowif.events.infra.InMemoryEventStore;
import com.rnowif.events.infra.SimpleEventBus;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StoreEventsFeature {

    @Test
    public void should_store_event_when_it_is_published() {
        EventStore eventStore = new InMemoryEventStore();
        SimpleEventBus eventBus = new SimpleEventBus(eventStore);

        FakeEvent event = new FakeEvent(FakeEvent.Id.of(1L));
        eventBus.publish(event);

        List<Event> eventsOfEntity = eventStore.getByEntity(event.getEntityId());
        assertThat(eventsOfEntity.size(), is(1));
        assertThat(eventsOfEntity.get(0), is(event));
    }
}
