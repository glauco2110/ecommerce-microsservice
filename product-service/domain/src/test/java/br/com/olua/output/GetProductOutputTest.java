package br.com.olua.output;

import br.com.olua.model.Product;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GetProductOutputTest {

    private Product getProduct(String id, String name, String description, String category, double price) {
        return Product.of(id, name, description, category, price);
    }

    @Test
    void givenAValidProduct_whenFrom_thenShouldReturnProductOutput() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        Product product = getProduct(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var output = GetProductOutput.from(product);

        assertEquals(expectedId, output.getId());
        assertEquals(expectedName, output.getName());
        assertEquals(expectedDescription, output.getDescription());
        assertEquals(expectedCategory, output.getCategory());
        assertEquals(expectedPrice, output.getPrice());
    }

    @Test
    void givenANullableProduct_whenFrom_thenShouldReturnNull() {
        Product product = null;

        final var output = GetProductOutput.from(product);

        assertNull(output);
    }
}
