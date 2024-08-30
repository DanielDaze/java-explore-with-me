package ru.practicum.server.request.service;

import ru.practicum.server.request.model.Request;

import java.util.Collection;

public interface RequestService {
    Request create(long userId, long eventId);
    Collection<Request> getAll(long userId);
    Request cancel(long userId, long requestId);
}
