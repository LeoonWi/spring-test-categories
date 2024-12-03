package ru.justwi.categories.service;

import ru.justwi.categories.model.Category;
import ru.justwi.categories.util.ApiResponse;

public interface CategoryService {

    ApiResponse addCategory(Category category);
    ApiResponse get(Long id);
    ApiResponse getAll();
    ApiResponse getTree();
    ApiResponse remove(Long id);
    ApiResponse update(Long id, Category category);
    ApiResponse change_parent(Long id, Long new_parent);
    ApiResponse exchange_place(Long id_A, Long id_B);
}
