package ru.practicum.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.models.dto.EndpointHitDto;
import ru.practicum.models.dto.ViewStats;
import ru.practicum.service.model.mapper.EndpointHitMapper;
import ru.practicum.service.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MainService {
    private final HitRepository repository;

    public EndpointHitDto saveHit(EndpointHitDto dto) {
        return EndpointHitMapper.maptoEndpointHitDto(repository.save(EndpointHitMapper.mapToEndpointHit(dto)));
    }

    public List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        List<ViewStats> viewStatsList = new ArrayList<>();
        if (unique) {
            for (String s : uris) {
                ViewStats viewStats = repository.getViewStatsUnique(start, end, s);
                viewStatsList.add(Objects.requireNonNullElseGet(viewStats, () -> new ViewStats("ewm-main-service", s, 0L)));
            }
        } else {
            for (String s : uris) {
                ViewStats viewStats = repository.getViewStats(start, end, s);
                viewStatsList.add(Objects.requireNonNullElseGet(viewStats, () -> new ViewStats("ewm-main-service", s, 0L)));
            }
        }
        viewStatsList.sort(Comparator.comparing(ViewStats::getHits).reversed());
        return viewStatsList;
    }
}
