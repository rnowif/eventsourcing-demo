package com.rnowif.events.infra;

import com.rnowif.events.domain.EntityId;
import com.rnowif.events.domain.Event;
import com.rnowif.events.domain.EventStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

public class InMemoryEventStore implements EventStore {

    private final Map<EntityId, List<Event>> events;

    public InMemoryEventStore() {
        this.events = new HashMap<>();
    }

    @Override
    public void save(Event event) {
        events.putIfAbsent(event.getEntityId(), new ArrayList<>());
        events.get(event.getEntityId()).add(event);
    }

    @Override
    public List<Event> getByEntity(EntityId id) {
        return events.getOrDefault(id, emptyList());
    }
}
