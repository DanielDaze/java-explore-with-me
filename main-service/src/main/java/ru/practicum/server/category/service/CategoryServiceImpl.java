package ru.practicum.server.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.server.category.model.Category;
import ru.practicum.server.category.model.dto.CategoryDto;
import ru.practicum.server.category.model.dto.CategoryMapper;
import ru.practicum.server.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category create(CategoryDto catDto) {
        Category category = categoryRepository.save(CategoryMapper.mapToCategory(catDto));
        log.info("POST /admin/categories -> returning from db {}", category);
        return category;
    }

    @Override
    @Transactional
    public void delete(long catId) {
        categoryRepository.deleteById(catId);
        log.info("DELETE /admin/categories/{} -> deleted from db", catId);
    }

    @Override
    @Transactional
    public Category patch(long catId, CategoryDto catDto) {
        Category categoryToSave = CategoryMapper.mapToCategory(catDto);
        categoryToSave.setId(catId);
        Category category = categoryRepository.save(categoryToSave);
        log.info("PATCH /admin/categories/{} -> {}", catId, category);
        return category;    }
}
