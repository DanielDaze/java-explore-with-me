package ru.practicum.server.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.server.request.model.Request;
import ru.practicum.server.request.service.RequestService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/users/{userId}/requests")
    public Request create(@PathVariable long userId, @RequestParam long eventId) {
        log.info("POST /users/{}/requests <- with eventId={}", userId, eventId);
        return requestService.create(userId, eventId);
    }

    @GetMapping("/users/{userId}/requests")
    public Collection<Request> getAll(@PathVariable long userId) {
        log.info("GET /users/{}/requests <-", userId);
        return requestService.getAll(userId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public Request cancel(@PathVariable long userId, @PathVariable long requestId) {
        log.info("PATCH /users/{}/requests/{}/cancel", userId, requestId);
        return requestService.cancel(userId, requestId);
    }
}
