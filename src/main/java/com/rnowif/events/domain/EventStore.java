package com.rnowif.events.domain;

import java.util.List;

public interface EventStore {

    void save(Event event);

    List<Event> getByEntity(EntityId id);
}
