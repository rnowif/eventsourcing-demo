package com.rnowif.events.infra;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.rnowif.events.domain.Event;
import com.rnowif.events.fixtures.SimpleEvent;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static java.util.stream.LongStream.range;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitQuickcheck.class)
public class InMemoryEventStoreTest {

    @Test
    public void should_get_no_event_where_none_is_stored() {
        InMemoryEventStore eventStore = new InMemoryEventStore();
        assertThat(eventStore.getByEntity(SimpleEvent.Id.of(1L)).size(), is(0));
    }
    
    @Property
    public void should_get_an_event_when_an_event_is_stored(
            @InRange(min = "1", max = "100") int nbEvents,
            @InRange(min = "1", max = "100") long idValue
    ) {
        InMemoryEventStore eventStore = new InMemoryEventStore();

        SimpleEvent.Id id = SimpleEvent.Id.of(idValue);
        range(0, nbEvents).forEach(i -> eventStore.save(new SimpleEvent(id)));

        List<Event> eventsOfEntity = eventStore.getByEntity(id);
        assertThat(eventsOfEntity.size(), is(nbEvents));
    }

}