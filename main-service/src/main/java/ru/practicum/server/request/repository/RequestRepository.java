package ru.practicum.server.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.server.request.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByEventIdAndRequesterId(long eventId, long userId);
    List<Request> findAllByEventId(long eventId);
    List<Request> findAllByRequesterId(long userId);
}
