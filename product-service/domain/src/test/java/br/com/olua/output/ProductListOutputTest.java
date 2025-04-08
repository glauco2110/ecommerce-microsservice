package br.com.olua.output;

import br.com.olua.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductListOutputTest {


    private Product getProduct(String id, String name, String description, String category, double price) {
        return Product.of(id, name, description, category, price);
    }

    @Test
    void givenAListOfProducts_whenFrom_thenGetAllProductOutputList() {
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

        assertNotNull(output);
        assertNotNull(output.get(0));
        assertNotNull(output.get(1));

        assertEquals(expedtedId1, output.get(0).getId());
        assertEquals(expedtedName1, output.get(0).getName());
        assertEquals(expedtedPrice1, output.get(0).getPrice());
        assertEquals(expedtedId2, output.get(1).getId());
        assertEquals(expedtedName2, output.get(1).getName());
        assertEquals(expedtedPrice2, output.get(1).getPrice());

    }

    @Test
    void givenAnEmptyListOfProducts_whenFrom_thenEmptyGetAllProductOutputList() {
        final List<Product> products = List.of();

        final var output = ProductListOutput.from(products);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

    @Test
    void givenANullListOfProducts_whenFrom_thenEmptyGetAllProductOutputList() {
        final var output = ProductListOutput.from(null);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

}
