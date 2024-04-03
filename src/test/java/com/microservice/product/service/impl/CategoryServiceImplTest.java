package com.microservice.product.service.impl;

import com.microservice.product.entity.Category;
import com.microservice.product.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCategoryById_existingId_shouldReturnCategory() {
        // Given
        long id = 1L;
        Category category = new Category();
        category.setId(id);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        // When
        Category result = categoryService.getCategoryById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getCategoryById_nonExistingId_shouldReturnNull() {
        // Given
        long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Category result = categoryService.getCategoryById(id);

        // Then
        assertNull(result);
    }

    @Test
    void getAllCategories_shouldReturnListOfCategories() {
        // Given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertNotNull(result);
        assertEquals(categories.size(), result.size());
    }

    @Test
    void createCategory_validCategory_shouldReturnCreatedCategory() {
        // Given
        Category category = new Category();
        when(categoryRepository.save(category)).thenReturn(category);

        // When
        Category result = categoryService.createCategory(category);

        // Then
        assertNotNull(result);
    }

    @Test
    void updateCategory_existingIdAndValidCategory_shouldReturnUpdatedCategory() {
        // Given
        long id = 1L;
        Category category = new Category();
        category.setId(id);
        when(categoryRepository.existsById(id)).thenReturn(true);
        when(categoryRepository.save(category)).thenReturn(category);

        // When
        Category result = categoryService.updateCategory(id, category);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void updateCategory_nonExistingId_shouldReturnNull() {
        // Given
        long id = 1L;
        Category category = new Category();
        when(categoryRepository.existsById(id)).thenReturn(false);

        // When
        Category result = categoryService.updateCategory(id, category);

        // Then
        assertNull(result);
    }

    @Test
    void deleteCategory_existingId_shouldDeleteCategory() {
        // Given
        long id = 1L;
        when(categoryRepository.existsById(id)).thenReturn(true);

        // When
        categoryService.deleteCategory(id);

        // Then
        verify(categoryRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteCategory_nonExistingId_shouldNotDeleteCategory() {
        // Given
        long id = 1L;
        when(categoryRepository.existsById(id)).thenReturn(false);

        // When
        categoryService.deleteCategory(id);

        // Then
        verify(categoryRepository, never()).deleteById(id);
    }
}