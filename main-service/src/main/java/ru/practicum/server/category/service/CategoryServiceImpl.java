package ru.practicum.server.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.server.category.model.Category;
import ru.practicum.server.category.model.dto.CategoryDto;
import ru.practicum.server.category.model.dto.CategoryMapper;
import ru.practicum.server.category.repository.CategoryRepository;
import ru.practicum.server.exception.NotFoundException;

import java.util.Collection;

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
        categoryRepository.findById(catId).orElseThrow(NotFoundException::new);
        categoryRepository.deleteById(catId);
        log.info("DELETE /admin/categories/{} -> deleted from db", catId);
    }

    @Override
    @Transactional
    public Category patch(long catId, CategoryDto catDto) {
        Category categoryToSave = CategoryMapper.mapToCategory(catDto);
        categoryToSave.setId(catId);
        categoryRepository.findById(catId).orElseThrow(NotFoundException::new);
        Category saved = categoryRepository.save(categoryToSave);
        log.info("PATCH /admin/categories/{} -> {}", catId, saved);
        return saved;
    }

    @Override
    public Collection<Category> getAll(int from, int size) {
        log.info("GET /categories -> returning all entries from db");
        return categoryRepository.findByParameters(from, size);
    }

    @Override
    public Category get(long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(NotFoundException::new);
        log.info("GET /categories/{} -> returning from db {}", catId, category);
        return category;
    }
}
