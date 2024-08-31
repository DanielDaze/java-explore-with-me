package ru.practicum.server.event.service;

import ru.practicum.server.event.model.Event;
import ru.practicum.server.event.model.EventState;
import ru.practicum.server.event.model.dto.EventDto;
import ru.practicum.server.event.model.dto.EventDtoAdminPatch;
import ru.practicum.server.event.model.dto.EventDtoPatch;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventService {
    Event create(long userId, EventDto eventDto);
    Event get(long userId, long eventId);
    Collection<Event> getAll(long userId, int from, int size);
    Event update(long userId, long eventId, EventDtoPatch eventDto);
    Event adminUpdate(long eventId, EventDtoAdminPatch eventDto);
    Collection<Event> adminGet(Long[] users, EventState[] states, Long[] categories, LocalDateTime rangeStart,
                               LocalDateTime rangeEnd, int from, int size);
}
