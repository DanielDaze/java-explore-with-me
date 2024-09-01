package ru.practicum.server.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.server.event.model.Event;
import ru.practicum.server.event.model.EventState;
import ru.practicum.server.event.repository.EventRepository;
import ru.practicum.server.exception.InvalidDataException;
import ru.practicum.server.exception.NotFoundException;
import ru.practicum.server.request.model.Request;
import ru.practicum.server.request.model.RequestStatus;
import ru.practicum.server.request.model.dto.RequestUpdateDto;
import ru.practicum.server.request.repository.RequestRepository;
import ru.practicum.server.user.model.User;
import ru.practicum.server.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Request create(long userId, long eventId) {
        User requester = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);

        Request request = new Request(null, LocalDateTime.now(), event, requester, RequestStatus.PENDING);
        validateRequest(request);
        Request saved = requestRepository.save(request);
        log.info("POST /users/{}/requests -> returning from db {}", userId, saved);
        return saved;
    }

    private void validateRequest(Request request) {
        requestRepository.findByEventIdAndRequesterId(request.getEvent().getId(), request.getRequester().getId()).ifPresent(value -> {
            throw new InvalidDataException("Вы уже отправили заявку на это событие!");
        });
        if (request.getEvent().getInitiator().getId().equals(request.getRequester().getId())) {
            throw new InvalidDataException("Вы не можете отправить заявку на участии в собственном событии!");
        }
        if (request.getEvent().getState() != EventState.PUBLISHED) {
            throw new InvalidDataException("Это событие еще не было опубликовано!");
        }
        List<Request> requestsForEvent = requestRepository.findAllByEventId(request.getEvent().getId());
        if (requestsForEvent.size() == request.getEvent().getParticipantLimit()) {
            throw new InvalidDataException("Превышен лимит заявок на это событие!");
        }
        if (!request.getEvent().getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Request> getAll(long userId) {
        List<Request> requests = requestRepository.findAllByRequesterId(userId);
        log.info("GET /users/{}/requests -> returning from db", userId);
        return requests;
    }

    @Override
    @Transactional
    public Request cancel(long userId, long requestId) {
        userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Request request = requestRepository.findById(requestId).orElseThrow(NotFoundException::new);
        request.setStatus(RequestStatus.CANCELED);
        Request saved = requestRepository.save(request);
        Event event = request.getEvent();
        event.setConfirmedRequests(event.getConfirmedRequests() - 1);
        eventRepository.save(event);
        log.info("PATCH /users/{}/requests/{}/cancel -> returning from db {}", userId, requestId, saved);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Request> getRequestsForEvent(long userId, long eventId) {
        eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        List<Request> requests = requestRepository.findAllByEventId(eventId);
        log.info("GET /users/{}/events/{}/requests -> returning form db {}", userId, eventId, requests);
        return requests;
    }

    @Override
    @Transactional
    public Collection<Request> confirmRequests(long userId, long eventId, RequestUpdateDto dto) {
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        List<Request> confirmedRequests = requestRepository.findAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
        List<Request> requests = requestRepository.findAllById(dto.getRequestIds());
        if (!requests.isEmpty()) {
            requests = requests.stream().filter(request -> request.getStatus() == RequestStatus.PENDING).toList();
            if (!requests.isEmpty()) {
                if (dto.getStatus().equals("CONFIRMED")) {
                    requests = requests.stream().peek(request -> request.setStatus(RequestStatus.CONFIRMED)).toList();
                    for (Request request : requests) {
                        if (confirmedRequests.size() == request.getEvent().getParticipantLimit()) {
                            throw new InvalidDataException("Превышен лимит участников для этого события!");
                        } else {
                            request.setStatus(RequestStatus.CONFIRMED);
                            confirmedRequests.add(request);
                            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                        }
                    }
                } else if (dto.getStatus().equals("REJECTED")) {
                    requests = requests.stream().peek(request -> request.setStatus(RequestStatus.REJECTED)).toList();
                }
                List<Request> saved = requestRepository.saveAll(requests);
                log.info("PATCH /users/{}/events/{}/requests -> returning from db {}", userId, eventId, requests);
                return saved;
            }
        }
        eventRepository.save(event);
        log.info("PATCH /users/{}/events/{}/requests -> returning from db {}", userId, eventId, requests);
        return List.of();
    }
}
