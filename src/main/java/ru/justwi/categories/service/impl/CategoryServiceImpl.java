package ru.justwi.categories.service.impl;

import org.springframework.stereotype.Service;
import ru.justwi.categories.model.Category;
import ru.justwi.categories.entity.CategoryEntity;
import ru.justwi.categories.repository.CategoryRepository;
import ru.justwi.categories.service.CategoryService;
import ru.justwi.categories.util.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    @Override
    public ApiResponse addCategory(Category category) {
        CategoryEntity mycategory = new CategoryEntity(category.getText());
        if (category.getParent() != null) {
            Optional<CategoryEntity> parent = repository.findById(category.getParent());
            if (parent.isPresent()) {
                parent.ifPresent(mycategory::setParent);
                parent.get().getChildren().add(mycategory);
            }
        }
        repository.save(mycategory);
        return new ApiResponse("Категория добавлена", 200, null);
    }

    @Override
    public ApiResponse getAll() {
        List<Category> categories = repository.findAllByOrderByIdAsc()
                .stream()
                .map(Category::new)
                .collect(Collectors.toList());
        return new ApiResponse(200, categories);
    }

    @Override
    public ApiResponse getTree() {
        List<CategoryEntity> categories = repository.findAllByOrderByIdAsc();
        // представление данных для удобного поиска элементов по ключу
        Map<Long, CategoryEntity> mapCategories = categories.stream()
                .collect(Collectors.toMap(CategoryEntity::getId, category -> category));

        List<CategoryEntity> tree = new ArrayList<>();
        for(CategoryEntity category : categories) {
            if (category.getParent() != null) {
                CategoryEntity parent = mapCategories.get(category.getParent().getId());
                parent.getChildren().add(category);
            } else {
                tree.add(category);
            }
        }
        return new ApiResponse(200, tree);
    }

    @Override
    public ApiResponse get(Long id) {
        Optional<CategoryEntity> category = repository.findById(id);
        if (category.isPresent()) {
            return new ApiResponse(200, Category.toModel(category.get()));
        } else {
            return new ApiResponse("Категория не найдена", 404, null);
        }
    }

    @Override
    public ApiResponse update(Long id, Category model) {
        Optional<CategoryEntity> category = repository.findById(id);
        if (category.isPresent()) {
            if (model.getText() != null) {
                category.get().setText(model.getText());
            }
            repository.save(category.get());
            return new ApiResponse(200, model);
        } else {
            return new ApiResponse("Категория не найдена", 404, null);
        }
    }

    @Override
    public ApiResponse change_parent(Long id, Long new_parent) {
        Optional<CategoryEntity> category = repository.findById(id);
        Optional<CategoryEntity> newParent = repository.findById(new_parent);

        if (category.isPresent() && newParent.isPresent()) {
            category.get().setParent(newParent.get());
            /*
            * Изменяем только родителя категории, т.к. в БД не хранится список детей.
            * Сам список создан для работы Hibernate который сам создает связи.
            */
            repository.save(category.get());
            return new ApiResponse(200, new Category(category.get()));
        } else {
            return new ApiResponse("Категория не найдена", 404, null);
        }
    }

    @Override
    public ApiResponse exchange_place(Long id_A, Long id_B) {
        Optional<CategoryEntity> category_A = repository.findById(id_A);
        Optional<CategoryEntity> category_B = repository.findById(id_B);

        if (category_A.isPresent() && category_B.isPresent()) {
            CategoryEntity catA = category_A.get();
            CategoryEntity catB = category_B.get();

            List<CategoryEntity> childrenA = new ArrayList<>(catA.getChildren());
            List<CategoryEntity> childrenB = new ArrayList<>(catB.getChildren());

            CategoryEntity tempParent = catA.getParent() != null ? catA.getParent() : null;

            catA.setParent(catB.getParent() != null ? catB.getParent() : null);
            catB.setParent(tempParent);

            for(CategoryEntity category : childrenA) {
                category.setParent(catB);
                repository.save(category);
            }
            for(CategoryEntity category : childrenB) {
                category.setParent(catA);
                repository.save(category);
            }

            repository.save(catA);
            repository.save(catB);
            return new ApiResponse("Ротация успешна", 200, null);
        } else {
            return new ApiResponse("Категория не найдена", 404, null);
        }
    }

    @Override
    public ApiResponse remove(Long id) {
        Optional<CategoryEntity> category = repository.findById(id);
        if (category.isPresent()) {
            repository.delete(category.get());
            return new ApiResponse("Категория удалена", 200, id);
        } else {
            return new ApiResponse("Категория не найдена", 404, null);
        }
    }

}
