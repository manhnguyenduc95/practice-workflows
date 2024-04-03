package com.microservice.product.controller;

import com.microservice.product.entity.Product;
import com.microservice.product.service.ProductService;
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

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getProductById_existingId_shouldReturnProduct() {
        // Given
        long id = 1L;
        Product product = new Product();
        product.setId(id);
        when(productService.getProductById(id)).thenReturn(product);

        // When
        ResponseEntity<Product> response = productController.getProductById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void getProductById_nonExistingId_shouldReturnNotFound() {
        // Given
        long id = 1L;
        when(productService.getProductById(id)).thenReturn(null);

        // When
        ResponseEntity<Product> response = productController.getProductById(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllProducts_shouldReturnListOfProducts() {
        // Given
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productService.getAllProducts()).thenReturn(products);

        // When
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(products.size(), response.getBody().size());
    }

    @Test
    void createProduct_validProduct_shouldReturnCreatedProduct() {
        // Given
        Product product = new Product();
        when(productService.createProduct(product)).thenReturn(product);

        // When
        ResponseEntity<Product> response = productController.createProduct(product);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateProduct_existingIdAndValidProduct_shouldReturnUpdatedProduct() {
        // Given
        long id = 1L;
        Product product = new Product();
        product.setId(id);
        when(productService.updateProduct(id, product)).thenReturn(product);

        // When
        ResponseEntity<Product> response = productController.updateProduct(id, product);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void updateProduct_nonExistingId_shouldReturnNotFound() {
        // Given
        long id = 1L;
        Product product = new Product();
        when(productService.updateProduct(id, product)).thenReturn(null);

        // When
        ResponseEntity<Product> response = productController.updateProduct(id, product);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteProduct_existingId_shouldReturnNoContent() {
        // Given
        long id = 1L;

        // When
        ResponseEntity<Void> response = productController.deleteProduct(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteProduct_nonExistingId_shouldReturnNoContent() {
        // Given
        long id = 1L;
        doNothing().when(productService).deleteProduct(id); // When the product does not exist, no action is performed

        // When
        ResponseEntity<Void> response = productController.deleteProduct(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}