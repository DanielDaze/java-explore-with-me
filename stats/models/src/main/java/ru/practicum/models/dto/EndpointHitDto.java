package ru.practicum.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
