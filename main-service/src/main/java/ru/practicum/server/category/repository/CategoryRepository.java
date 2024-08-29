package ru.practicum.server.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.server.category.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(nativeQuery = true, value = "select * from categories where id > ?1 limit ?2")
    List<Category> findByParameters(int from, int size);
}
