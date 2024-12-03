package ru.justwi.categories.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String text;
    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private CategoryEntity parent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
    @JsonManagedReference
    private List<CategoryEntity> children;

    public CategoryEntity() {
    }

    public CategoryEntity(String text) {
        this.text = text;
    }

    public CategoryEntity(String text, CategoryEntity parent) {
        this.text = text;
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CategoryEntity getParent() {
        return parent;
    }

    public void setParent(CategoryEntity parent) {
        this.parent = parent;
    }

    public List<CategoryEntity> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryEntity> children) {
        this.children = children;
    }
}
