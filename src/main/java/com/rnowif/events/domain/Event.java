package com.rnowif.events.domain;

import java.time.Instant;

public interface Event {

    EntityId getEntityId();

    Instant getTimestamp();
}
