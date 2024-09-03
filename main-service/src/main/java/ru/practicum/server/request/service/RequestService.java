package ru.practicum.server.request.service;

import ru.practicum.server.request.model.Request;
import ru.practicum.server.request.model.dto.RequestDto;
import ru.practicum.server.request.model.dto.RequestUpdateDto;

import java.util.Collection;

public interface RequestService {
    RequestDto create(long userId, long eventId);

    Collection<RequestDto> getAll(long userId);

    RequestDto cancel(long userId, long requestId);

    Collection<RequestDto> getRequestsForEvent(long userId, long eventId);

    Collection<Request> confirmRequests(long userId, long eventId, RequestUpdateDto dto);
}
