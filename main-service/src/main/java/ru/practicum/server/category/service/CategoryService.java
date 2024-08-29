package ru.practicum.server.category.service;

import ru.practicum.server.category.model.Category;
import ru.practicum.server.category.model.dto.CategoryDto;

public interface CategoryService {
    Category create(CategoryDto catDto);
    void delete(long catId);
    Category patch(long catId, CategoryDto catDto);
}
