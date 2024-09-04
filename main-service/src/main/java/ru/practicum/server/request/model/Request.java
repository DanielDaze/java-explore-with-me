package ru.practicum.server.request.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.server.event.model.Event;
import ru.practicum.server.user.model.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    @ToString.Exclude
    User requester;

    @Enumerated(EnumType.STRING)
    RequestStatus status;
}
