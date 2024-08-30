package ru.practicum.server.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.server.category.model.Category;
import ru.practicum.server.category.repository.CategoryRepository;
import ru.practicum.server.event.model.Event;
import ru.practicum.server.event.model.EventState;
import ru.practicum.server.event.model.dto.EventDto;
import ru.practicum.server.event.model.dto.EventDtoPatch;
import ru.practicum.server.event.model.dto.EventMapper;
import ru.practicum.server.event.repository.EventRepository;
import ru.practicum.server.exception.InvalidDataException;
import ru.practicum.server.exception.NotFoundException;
import ru.practicum.server.user.model.User;
import ru.practicum.server.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Event create(long userId, EventDto eventDto) {
        User initiator = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Category category = categoryRepository.findById(eventDto.getCategory()).orElseThrow(NotFoundException::new);
        Event event = EventMapper.mapToEvent(eventDto);
        event.setInitiator(initiator);
        event.setCategory(category);
        setDefaultValuesOnCreate(event);
        checkCorrectEventDate(event.getEventDate());
        Event saved = eventRepository.save(event);
        log.info("POST /users/{}/events -> returning from db {}", userId, saved);
        return eventRepository.save(saved);
    }

    private void setDefaultValuesOnCreate(Event event) {
        event.setViews(0);
        event.setConfirmedRequests(0);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
    }

    private void checkCorrectEventDate(LocalDateTime date) {
        if (date.minusHours(2L).isBefore(LocalDateTime.now())) {
            throw new InvalidDataException("Ваше событие начинается раньше, чем через два часа!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Event get(long userId, long eventId) {
        userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        log.info("GET /users/{}/events/{} -> returning from db {}", userId, eventId, event);
        return event;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Event> getAll(long userId, int from, int size) {
        userRepository.findById(userId).orElseThrow(NotFoundException::new);
        List<Event> events = eventRepository.findByParameters(userId, from, size);
        log.info("GET /users/{}/events ->", userId);
        return events;
    }

    @Override
    @Transactional
    public Event update(long userId, long eventId, EventDtoPatch eventDto) {
        userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Event old = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        if (old.getState() != EventState.PENDING) {
            throw new InvalidDataException("Вы уже не можете изменить это событие!");
        }
        checkCorrectEventDate(eventDto.getEventDate());
        if (eventDto.getCategory() != null) {
            Category category = categoryRepository.findById(eventDto.getCategory()).orElseThrow(NotFoundException::new);
            old.setCategory(category);
        }

        Event saved = eventRepository.save(EventMapper.updateEvent(old, eventDto));
        log.info("PATCH /users/{}/events/{} -> returning from db {}", userId, eventId, saved);
        return saved;
    }
}
