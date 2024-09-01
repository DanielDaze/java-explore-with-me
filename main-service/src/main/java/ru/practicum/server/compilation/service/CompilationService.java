package ru.practicum.server.compilation.service;

import ru.practicum.server.compilation.model.Compilation;
import ru.practicum.server.compilation.model.dto.CompilationDto;

import java.util.Collection;

public interface CompilationService {
    Compilation create(CompilationDto compDto);
    void delete(long compId);
    Compilation update(long compId, CompilationDto compDto);
    Compilation get(long compId);
    Collection<Compilation> getFiltered(boolean pinned, int from, int size);
}
