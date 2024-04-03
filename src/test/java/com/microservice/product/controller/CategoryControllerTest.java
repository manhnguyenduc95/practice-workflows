package com.microservice.product.controller;

import com.microservice.product.entity.Category;
import com.microservice.product.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

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
        when(categoryService.getCategoryById(id)).thenReturn(category);

        // When
        ResponseEntity<Category> response = categoryController.getCategoryById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void getCategoryById_nonExistingId_shouldReturnNotFound() {
        // Given
        long id = 1L;
        when(categoryService.getCategoryById(id)).thenReturn(null);

        // When
        ResponseEntity<Category> response = categoryController.getCategoryById(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllCategories_shouldReturnListOfCategories() {
        // Given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());
        when(categoryService.getAllCategories()).thenReturn(categories);

        // When
        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(categories.size(), response.getBody().size());
    }

    @Test
    void createCategory_validCategory_shouldReturnCreatedCategory() {
        // Given
        Category category = new Category();
        when(categoryService.createCategory(category)).thenReturn(category);

        // When
        ResponseEntity<Category> response = categoryController.createCategory(category);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateCategory_existingIdAndValidCategory_shouldReturnUpdatedCategory() {
        // Given
        long id = 1L;
        Category category = new Category();
        category.setId(id);
        when(categoryService.updateCategory(id, category)).thenReturn(category);

        // When
        ResponseEntity<Category> response = categoryController.updateCategory(id, category);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void updateCategory_nonExistingId_shouldReturnNotFound() {
        // Given
        long id = 1L;
        Category category = new Category();
        when(categoryService.updateCategory(id, category)).thenReturn(null);

        // When
        ResponseEntity<Category> response = categoryController.updateCategory(id, category);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteCategory_existingId_shouldReturnNoContent() {
        // Given
        long id = 1L;

        // When
        ResponseEntity<Void> response = categoryController.deleteCategory(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteCategory_nonExistingId_shouldReturnNoContent() {
        // Given
        long id = 1L;
        doNothing().when(categoryService).deleteCategory(id); // When the category does not exist, no action is performed

        // When
        ResponseEntity<Void> response = categoryController.deleteCategory(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
