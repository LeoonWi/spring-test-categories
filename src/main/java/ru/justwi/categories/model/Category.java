package ru.justwi.categories.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.justwi.categories.entity.CategoryEntity;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {
    private Long id;
    private String text;
    private Long parent;
    private List<Long> children;

    public Category() {
    }

    public Category(String text) {
        this.text = text;
    }

    public Category(String text, Long parent) {
        this.text = text;
        this.parent = parent;
    }

    public Category(String text, List<Long> children) {
        this.text = text;
        this.children = children;
    }

    public Category(String text, Long parent, List<Long> children) {
        this.text = text;
        this.parent = parent;
        this.children = children;
    }

    public Category(Long id, String text, Long parent, List<Long> children) {
        this.id = id;
        this.text = text;
        this.parent = parent;
        this.children = children;
    }

    public Category(CategoryEntity category) {
        id = category.getId();
        text = category.getText();
        parent = category.getParent() != null ? category.getParent().getId() : null;
    }

    public static Category toModel(CategoryEntity category) {
        Category model = new Category();
        model.setText(category.getText());
        model.setParent(category.getParent() != null ? category.getParent().getId() : null);
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }
}
