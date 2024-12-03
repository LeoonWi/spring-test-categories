package ru.justwi.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.justwi.categories.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByText(String text);
    List<CategoryEntity> findAllByOrderByIdAsc();
}