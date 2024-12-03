package ru.justwi.categories.controller;

import org.springframework.web.bind.annotation.*;
import ru.justwi.categories.model.Category;
import ru.justwi.categories.service.CategoryService;
import ru.justwi.categories.util.ApiResponse;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ApiResponse addCategory(@RequestBody Category category) {
        try {
            return service.addCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Неверные данные", 500, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse getCategories() {
        try {
            return service.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Неверные данные", 500, e.getMessage());
        }
    }

    @GetMapping
    public ApiResponse get(@RequestParam("id") Long id) {
        try {
            return service.get(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(500, e.getMessage());
        }
    }

    @GetMapping("/tree")
    public ApiResponse getTree() {
        try {
            return service.getTree();
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Неверные данные", 500, e.getMessage());
        }
    }

    @PutMapping
    public ApiResponse update(@RequestParam("id") Long id, @RequestBody Category category) {
        try {
            return service.update(id, category);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Неверные данные", 500, e.getMessage());
        }
    }

    @PutMapping("/set_parent")
    public ApiResponse change_parent(@RequestParam("id") Long id, @RequestParam("parentId") Long new_parent) {
        try {
            return service.change_parent(id, new_parent);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Неверные данные", 500, e.getMessage());
        }
    }

    @PutMapping("/exchange")
    public ApiResponse exchange_place(@RequestParam("id_A") Long id_A, @RequestParam("id_B") Long id_B) {
        try {
            return service.exchange_place(id_A, id_B);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Неверные данные", 500, e.getMessage());
        }
    }

    @DeleteMapping
    public ApiResponse remove(@RequestParam("id") Long id) {
        try {
            return service.remove(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Неверные данные", 500, e.getMessage());
        }
    }

}
