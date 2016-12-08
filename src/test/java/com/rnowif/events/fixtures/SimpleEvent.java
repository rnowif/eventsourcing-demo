package com.rnowif.events.fixtures;

import com.rnowif.events.domain.EntityId;
import com.rnowif.events.domain.Event;

import java.time.Instant;

public class SimpleEvent implements Event {

    private final Id id;
    private final Instant eventTime;

    public static class Id implements EntityId {
        private final long value;

        private Id(long value) {
            this.value = value;
        }

        public static Id of(long value) {
            return new Id(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Id id = (Id) o;

            return value == id.value;
        }

        @Override
        public int hashCode() {
            return (int) (value ^ (value >>> 32));
        }
    }

    public SimpleEvent(Id id) {
        this.id = id;
        this.eventTime = Instant.now();
    }

    @Override
    public EntityId getEntityId() {
        return id;
    }

    @Override
    public Instant getTimestamp() {
        return eventTime;
    }
}