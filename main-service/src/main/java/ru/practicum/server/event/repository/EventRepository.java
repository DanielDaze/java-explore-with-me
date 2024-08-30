package ru.practicum.server.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.server.event.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(nativeQuery = true, value = "select * from events where initiator_id = ?1 id > ?2 limit ?3")
    List<Event> findByParameters(long userId, int from, int size);
}
