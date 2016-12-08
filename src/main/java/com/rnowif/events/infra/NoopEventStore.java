package com.rnowif.events.infra;

import com.rnowif.events.domain.EntityId;
import com.rnowif.events.domain.Event;
import com.rnowif.events.domain.EventStore;

import java.util.List;

import static java.util.Collections.emptyList;

public class NoopEventStore implements EventStore {
    @Override
    public void save(Event event) {
        // NO-OP
    }

    @Override
    public List<Event> getByEntity(EntityId id) {
        return emptyList();
    }
}
