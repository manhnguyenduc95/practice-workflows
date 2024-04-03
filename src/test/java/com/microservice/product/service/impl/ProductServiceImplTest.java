package com.microservice.product.service.impl;

import com.microservice.product.entity.Product;
import com.microservice.product.repository.ProductRepository;
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

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

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
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // When
        Product result = productService.getProductById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getProductById_nonExistingId_shouldReturnNull() {
        // Given
        long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Product result = productService.getProductById(id);

        // Then
        assertNull(result);
    }

    @Test
    void getAllProducts_shouldReturnListOfProducts() {
        // Given
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = productService.getAllProducts();

        // Then
        assertNotNull(result);
        assertEquals(products.size(), result.size());
    }

    @Test
    void createProduct_validProduct_shouldReturnCreatedProduct() {
        // Given
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        // When
        Product result = productService.createProduct(product);

        // Then
        assertNotNull(result);
    }

    @Test
    void updateProduct_existingIdAndValidProduct_shouldReturnUpdatedProduct() {
        // Given
        long id = 1L;
        Product product = new Product();
        product.setId(id);
        when(productRepository.existsById(id)).thenReturn(true);
        when(productRepository.save(product)).thenReturn(product);

        // When
        Product result = productService.updateProduct(id, product);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void updateProduct_nonExistingId_shouldReturnNull() {
        // Given
        long id = 1L;
        Product product = new Product();
        when(productRepository.existsById(id)).thenReturn(false);

        // When
        Product result = productService.updateProduct(id, product);

        // Then
        assertNull(result);
    }

    @Test
    void deleteProduct_existingId_shouldDeleteProduct() {
        // Given
        long id = 1L;
        when(productRepository.existsById(id)).thenReturn(true);

        // When
        productService.deleteProduct(id);

        // Then
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteProduct_nonExistingId_shouldNotDeleteProduct() {
        // Given
        long id = 1L;
        when(productRepository.existsById(id)).thenReturn(false);

        // When
        productService.deleteProduct(id);

        // Then
        verify(productRepository, never()).deleteById(id);
    }
}
