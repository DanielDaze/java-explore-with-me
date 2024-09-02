package ru.practicum.server.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.server.event.model.Event;
import ru.practicum.server.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByInitiatorId(long userId, Pageable pageable);

    @Query(value = "select e from Event as e " +
                    "where e.initiator.id in ?1 " +
                    "and e.state in ?2 " +
                    "and e.category.id in ?3 " +
                    "and e.eventDate between ?4 and ?5")
    List<Event> adminGet(Long[] users, EventState[] states, Long[] categories, LocalDateTime rangeStart,
                         LocalDateTime rangeEnd, Pageable pageable);

    @Query("select e from Event as e " +
            "where (lower(e.annotation) LIKE lower(concat('%',:text,'%')) " +
            "OR lower(e.description) LIKE lower(CONCAT('%',:text,'%')) OR :text is null) " +
            "and e.category.id in (:categories) " +
            "AND e.paid = :paid " +
            "AND e.eventDate > :rangeStart " +
            "AND e.eventDate < :rangeEnd")
    List<Event> getEventsFiltered(String text, Long[] categories, Boolean paid,
                                LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
}
