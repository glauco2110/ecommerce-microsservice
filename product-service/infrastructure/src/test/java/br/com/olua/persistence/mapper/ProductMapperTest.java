package br.com.olua.persistence.mapper;

import br.com.olua.model.Product;
import br.com.olua.persistence.data.ProductData;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductMapperTest {

    @Test
    void givenAProductData_whenMapToDomain_thenReturnProduct() {
        final var mapper = new ProductMapper();

        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        ProductData data = new ProductData();
        data.setId(expectedId);
        data.setName(expectedName);
        data.setDescription(expectedDescription);
        data.setCategory(expectedCategory);
        data.setPrice(expectedPrice);

        Product product = mapper.domain(data);

        assertNotNull(product);
        assertEquals(expectedId, product.getId());
        assertEquals(expectedName, product.getName());
        assertEquals(expectedDescription, product.getDescription());
        assertEquals(expectedCategory, product.getCategory());
        assertEquals(expectedPrice, product.getPrice());

    }

    @Test
    void givenAProduct_whenMapToData_thenReturnProductData() {
        final var mapper = new ProductMapper();

        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        Product product = Product.of(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        ProductData data = mapper.data(product);

        assertNotNull(data);
        assertEquals(expectedId, data.getId());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedCategory, data.getCategory());
        assertEquals(expectedDescription, data.getDescription());
        assertEquals(expectedPrice, data.getPrice());
    }

}
