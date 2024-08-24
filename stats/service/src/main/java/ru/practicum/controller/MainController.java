package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.service.MainService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MainService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto saveHit(@RequestBody EndpointHitDto hit) {
        log.info("POST /hit <- {}", hit);
        return service.saveHit(hit);
    }

    @GetMapping("/stats")
    public List<ViewStats> getViewStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                        @RequestParam String[] uris, @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("GET /stats <- with start={}, end={} uri={} unique={}", start, end, uris, unique);
        return service.getViewStats(start, end, uris, unique);
    }
}
