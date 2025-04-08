package br.com.olua.output;

import br.com.olua.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAllProductOutputTest {

    private Product getProduct(String id, String name, String description, String category, double price) {
        return Product.of(id, name, description, category, price);
    }

    @Test
    void givenAValidOutput_whenCreateGetAllProductOutput_thenGetAllProductOutputIsCreated() {
        final var expedtedId1 = UUID.randomUUID().toString();
        final var expedtedName1 = UUID.randomUUID().toString();
        final var expedtedDescription1 = UUID.randomUUID().toString();
        final var expedtedCategory1 = UUID.randomUUID().toString();
        final var expedtedPrice1 = 10.0;

        final var expedtedId2 = UUID.randomUUID().toString();
        final var expedtedName2 = UUID.randomUUID().toString();
        final var expedtedDescription2 = UUID.randomUUID().toString();
        final var expedtedCategory2 = UUID.randomUUID().toString();
        final var expedtedPrice2 = 20.0;

        final var products = List.of(
                getProduct(expedtedId1, expedtedName1, expedtedDescription1, expedtedCategory1, expedtedPrice1),
                getProduct(expedtedId2, expedtedName2, expedtedDescription2, expedtedCategory2, expedtedPrice2)
        );

        final var output = ProductListOutput.from(products);

        final var getAllProductOutput = GetAllProductOutput.of(output, products.size());

        assertNotNull(getAllProductOutput);
        assertNotNull(getAllProductOutput.getProducts());
        assertEquals(2, getAllProductOutput.getTotal());
        assertEquals(expedtedId1, getAllProductOutput.getProducts().getFirst().getId());
        assertEquals(expedtedName1, getAllProductOutput.getProducts().getFirst().getName());
        assertEquals(expedtedPrice1, getAllProductOutput.getProducts().getFirst().getPrice());
        assertEquals(expedtedId2, getAllProductOutput.getProducts().get(1).getId());
        assertEquals(expedtedName2, getAllProductOutput.getProducts().get(1).getName());
        assertEquals(expedtedPrice2, getAllProductOutput.getProducts().get(1).getPrice());
    }

    @Test
    void givenAnEmptyListOfProducts_whenCreateGetAllProductOutput_thenEmptyGetAllProductOutputIsCreated() {
        final List<Product> products = List.of();

        final var output = ProductListOutput.from(products);

        final var getAllProductOutput = GetAllProductOutput.of(output, 0);

        assertNotNull(getAllProductOutput);
        assertNotNull(getAllProductOutput.getProducts());
        assertEquals(0, getAllProductOutput.getTotal());
    }

    @Test
    void givenAnNullListOfProducts_whenCreateGetAllProductOutput_thenEmptyGetAllProductOutputIsCreated() {
        final List<Product> products = null;

        final var output = ProductListOutput.from(products);

        final var getAllProductOutput = GetAllProductOutput.of(output, 0);

        assertNotNull(getAllProductOutput);
        assertNotNull(getAllProductOutput.getProducts());
        assertEquals(0, getAllProductOutput.getTotal());
    }
}
