package br.com.olua.command;

import br.com.olua.exceptions.DomainException;
import br.com.olua.model.Product;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateProductCommandTest {

    @Test
    void givenAValidCommand_whenCreateProduct_thenProductIsCreated() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        Product product = command.of();

        assertNotNull(product);
        assertEquals(expectedName, product.getName());
        assertEquals(expectedDescription, product.getDescription());
        assertEquals(expectedCategory, product.getCategory());
        assertEquals(expectedPrice, product.getPrice());
    }

    @Test
    void givenAnInvalidCommand_whenCreateProduct_thenProductIsNotCreated() {
        UpdateProductCommand command = new UpdateProductCommand(null,null, null, null, 0.0);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(5, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.name.required"));
        assertTrue(exception.getErrors().contains("product.description.required"));
        assertTrue(exception.getErrors().contains("product.category.required"));
        assertTrue(exception.getErrors().contains("product.price.invalid"));
        assertTrue(exception.getErrors().contains("product.id.required"));
    }

    @Test
    void givenAnEmptyProductName_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "";
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.name.required"));
    }

    @Test
    void givenAnNullProductName_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId,null, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.name.required"));
    }

    @Test
    void givenAnEmptyProductDescription_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName =  UUID.randomUUID().toString();
        final var expectedDescription = "";
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.description.required"));
    }

    @Test
    void givenAnNullProductDescription_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName =  UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, null, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.description.required"));
    }

    @Test
    void givenAnEmptyProductCategory_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName =  UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = "";
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.category.required"));
    }

    @Test
    void givenAnNullProductCategory_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName =  UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, null, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.category.required"));
    }

    @Test
    void givenAnInvalidProductPrice_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName =  UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory =  UUID.randomUUID().toString();
        final var expectedPrice = 0.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.price.invalid"));
    }
}
