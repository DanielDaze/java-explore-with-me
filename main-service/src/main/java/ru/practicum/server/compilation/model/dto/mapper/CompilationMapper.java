package ru.practicum.server.compilation.model.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.server.compilation.model.Compilation;
import ru.practicum.server.compilation.model.dto.CompilationDto;

@UtilityClass
public class CompilationMapper {
    public static Compilation mapToCompilation(CompilationDto dto) {
        return new Compilation(dto.getPinned(), dto.getTitle());
    }
}
