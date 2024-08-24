package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.model.mapper.EndpointHitMapper;
import ru.practicum.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                viewStatsList.add(repository.getViewStatsUnique(start, end, s));
            }
        } else {
            for (String s : uris) {
                viewStatsList.add(repository.getViewStats(start, end, s));
            }
        }
        return viewStatsList;
    }
}
