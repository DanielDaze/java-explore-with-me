package ru.practicum.server.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.server.like.model.LikeInfo;
import ru.practicum.server.like.model.LikeInfoId;

@Repository
public interface LikeRepository extends JpaRepository<LikeInfo, LikeInfoId> {
    @Query(value = "SELECT SUM(positive) " +
                    "FROM LikeInfo " +
                    "WHERE eventId = ?1")
    Integer findRatingByEventId(Long eventId);
}
