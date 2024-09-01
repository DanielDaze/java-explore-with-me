package ru.practicum.server.compilation.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class CompilationDto {
    private Set<Long> events;
    private Boolean pinned = false;
    @NotBlank
    private String title;
}
