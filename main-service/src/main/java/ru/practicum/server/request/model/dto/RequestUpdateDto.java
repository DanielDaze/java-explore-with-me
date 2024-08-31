package ru.practicum.server.request.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class RequestUpdateDto {
    List<Long> requestIds;
    String status;
}
