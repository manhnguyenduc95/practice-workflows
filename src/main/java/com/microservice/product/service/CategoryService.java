package com.microservice.product.service;

import com.microservice.product.entity.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}
