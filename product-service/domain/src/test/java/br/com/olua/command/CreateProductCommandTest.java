package br.com.olua.command;

import br.com.olua.exceptions.DomainException;
import br.com.olua.model.Product;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreateProductCommandTest {

    @Test
    void givenAValidCommand_whenCreateProduct_thenProductIsCreated() {
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

        Product product = command.of();

        assertNotNull(product);
        assertEquals(expectedName, product.getName());
        assertEquals(expectedDescription, product.getDescription());
        assertEquals(expectedCategory, product.getCategory());
        assertEquals(expectedPrice, product.getPrice());
    }

    @Test
    void givenAnInvalidCommand_whenCreateProduct_thenProductIsNotCreated() {
        CreateProductCommand command = new CreateProductCommand(null, null, null, 0.0);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(4, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.name.required"));
        assertTrue(exception.getErrors().contains("product.description.required"));
        assertTrue(exception.getErrors().contains("product.category.required"));
        assertTrue(exception.getErrors().contains("product.price.invalid"));
    }

    @Test
    void givenAnEmptyProductName_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedName = "";
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.name.required"));
    }

    @Test
    void givenAnNullProductName_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        CreateProductCommand command = new CreateProductCommand(null, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.name.required"));
    }

    @Test
    void givenAnEmptyProductDescription_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedName =  UUID.randomUUID().toString();
        final var expectedDescription = "";
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.description.required"));
    }

    @Test
    void givenAnNullProductDescription_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedName =  UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        CreateProductCommand command = new CreateProductCommand(expectedName, null, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.description.required"));
    }

    @Test
    void givenAnEmptyProductCategory_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedName =  UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = "";
        final var expectedPrice = 10.0;

        CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.category.required"));
    }

    @Test
    void givenAnNullProductCategory_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedName =  UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, null, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.category.required"));
    }

    @Test
    void givenAnInvalidProductPrice_whenCreateProduct_thenProductIsNotCreated() {
        final var expectedName =  UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory =  UUID.randomUUID().toString();
        final var expectedPrice = 0.0;

        CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, command::of);

        assertEquals("domain.validation.error", exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains("product.price.invalid"));
    }
}
