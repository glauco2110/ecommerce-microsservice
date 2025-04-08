package br.com.olua.model;

import br.com.olua.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void givenValidProduct_whenCreate_thenSuccess() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        final var product = Product.of(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        assertNotNull(product);
        assertEquals(expectedId, product.getId());
        assertEquals(expectedName, product.getName());
        assertEquals(expectedDescription, product.getDescription());
        assertEquals(expectedCategory, product.getCategory());
        assertEquals(expectedPrice, product.getPrice());

    }

    @Test
    void giverInvalidProduct_whenCreate_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "";
        final var expectedDescription = "";
        final var expectedCategory = "";
        final var expectedPrice = 0.0;

        final var exception = assertThrows(DomainException.class, () -> Product.of(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice));

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(4, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.name.required"));
        assertTrue(exception.getErrors().contains("product.description.required"));
        assertTrue(exception.getErrors().contains("product.category.required"));
        assertTrue(exception.getErrors().contains("product.price.invalid"));
        
    }

    @Test
    void givenEmptyProductName_whenCreate_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "";
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        final var exception = assertThrows(DomainException.class, () -> Product.of(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice));

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.name.required"));
    }

    @Test
    void givenSpaceProductName_whenCreate_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "   ";
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;


        final var exception = assertThrows(DomainException.class, () -> Product.of(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice));

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.name.required"));
    }

    @Test
    void givenNullProductName_whenCreate_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        final var exception = assertThrows(DomainException.class, () -> Product.of(expectedId, null, expectedDescription, expectedCategory, expectedPrice));

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.name.required"));
    }

    @Test
    void givenEmptyProductDescription_whenCreate_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = "";
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        final var exception = assertThrows(DomainException.class, () -> Product.of(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice));

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.description.required"));
    }

    @Test
    void givenNullProductDescription_whenCreate_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        final var exception = assertThrows(DomainException.class, () -> Product.of(expectedId, expectedName, null, expectedCategory, expectedPrice));

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.description.required"));
    }

    @Test
    void givenEmptyProductCategory_whenCreate_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = "";
        final var expectedPrice = 10.0;

        final var exception = assertThrows(DomainException.class, () -> Product.of(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice));

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.category.required"));
    }

    @Test
    void givenNullProductCategory_whenCreate_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        final var exception = assertThrows(DomainException.class, () -> Product.of(expectedId, expectedName, expectedDescription, null, expectedPrice));

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.category.required"));
    }

    @Test
    void givenInvalidProductPrice_whenCreate_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 0.0;

        final var exception = assertThrows(DomainException.class, () -> Product.of(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice));

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.price.invalid"));
    }

}
