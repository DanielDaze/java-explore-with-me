package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;

@Repository
public interface HitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.dto.ViewStats(eh.app, eh.uri, count(eh.id)) FROM EndpointHit AS eh WHERE eh.timestamp BETWEEN ?1 AND ?2 AND eh.uri = ?3")
    ViewStats getViewStats(LocalDateTime start, LocalDateTime end, String uri);

    @Query("SELECT new ru.practicum.dto.ViewStats(eh.app, eh.uri, count(DISTINCT eh.ip)) FROM EndpointHit AS eh WHERE eh.timestamp BETWEEN ?1 AND ?2 AND eh.uri = ?3")
    ViewStats getViewStatsUnique(LocalDateTime start, LocalDateTime end, String uri);
}
