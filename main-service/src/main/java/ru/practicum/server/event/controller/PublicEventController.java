package ru.practicum.server.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.server.event.model.Event;
import ru.practicum.server.event.model.dto.EventSearch;
import ru.practicum.server.event.service.EventService;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController("/events")
@RequiredArgsConstructor
@Slf4j
public class PublicEventController {
    private final EventService eventService;

    @GetMapping("/{eventId} <-")
    public Event get(@PathVariable long eventId, HttpServletRequest servletRequest) {
        log.info("GET /events/{}", eventId);
        return eventService.publicGet(eventId, servletRequest);
    }

    @GetMapping
    public Collection<Event> getSorted(@RequestParam String text, @RequestParam Long[] categories, @RequestParam Boolean paid,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable, @RequestParam EventSearch sort,
                                       @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size,
                                       HttpServletRequest servletRequest) {
        log.info("GET /events <-");
        return eventService.publicGetSorted(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, servletRequest);
    }
}
