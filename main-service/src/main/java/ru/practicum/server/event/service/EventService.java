package ru.practicum.server.event.service;

import ru.practicum.server.event.model.Event;
import ru.practicum.server.event.model.dto.EventDto;
import ru.practicum.server.event.model.dto.EventDtoPatch;

import java.util.Collection;

public interface EventService {
    Event create(long userId, EventDto eventDto);
    Event get(long userId, long eventId);
    Collection<Event> getAll(long userId, int from, int size);
    Event update(long userId, long eventId, EventDtoPatch eventDto);
}
