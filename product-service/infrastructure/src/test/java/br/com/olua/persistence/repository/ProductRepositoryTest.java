package br.com.olua.persistence.repository;

import br.com.olua.exceptions.NotFoundException;
import br.com.olua.model.Product;
import br.com.olua.output.GetAllProductOutput;
import br.com.olua.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@IntegrationTest
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepositoryImpl repository;

    private Product getProduct(String name, String description, String category, double price) {
        return Product.of(name, description, category, price);
    }

    private Product getProduct(String id, String name, String description, String category, double price) {
        return Product.of(id, name, description, category, price);
    }

    @Test
    void givenAValidProduct_WhenInserting_ShouldReturnTheProductIdPersisted() {
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 22.0;

        Product product = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var result = repository.insert(product);

        assertNotNull(result);

        final var productFound = repository.findById(result);

        assertTrue(productFound.isPresent());
        assertEquals(expectedName, productFound.get().getName());
        assertEquals(expectedDescription, productFound.get().getDescription());
        assertEquals(expectedCategory, productFound.get().getCategory());
        assertEquals(expectedPrice, productFound.get().getPrice());

    }

    @Test
    void givenAValidProduct_WhenUpdating_ShouldReturnTheProductIdPersisted() {
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 22.0;
        final var expectedNameUpdated = UUID.randomUUID().toString();

        Product product = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var result = repository.insert(product);

        assertNotNull(result);

        final var productFound = repository.findById(result);

        assertTrue(productFound.isPresent());
        assertEquals(expectedName, productFound.get().getName());
        assertEquals(expectedDescription, productFound.get().getDescription());
        assertEquals(expectedCategory, productFound.get().getCategory());
        assertEquals(expectedPrice, productFound.get().getPrice());

        product = getProduct(result, expectedNameUpdated, expectedDescription, expectedCategory, expectedPrice);

        final var resultUpdated = repository.update(product);

        assertNotNull(resultUpdated);
        assertEquals(result, resultUpdated);

        final var productUpdatedFound = repository.findById(result);

        assertTrue(productUpdatedFound.isPresent());
        assertEquals(expectedNameUpdated, productUpdatedFound.get().getName());
        assertEquals(expectedDescription, productUpdatedFound.get().getDescription());
        assertEquals(expectedCategory, productUpdatedFound.get().getCategory());
        assertEquals(expectedPrice, productUpdatedFound.get().getPrice());

    }

    @Test
    void givenAValidProductId_whenDeleting_ShouldReturnTheProductDeleted() {
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 22.0;

        Product product = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var result = repository.insert(product);

        assertNotNull(result);

        final var productFound = repository.findById(result);

        assertTrue(productFound.isPresent());
        assertEquals(expectedName, productFound.get().getName());
        assertEquals(expectedDescription, productFound.get().getDescription());
        assertEquals(expectedCategory, productFound.get().getCategory());
        assertEquals(expectedPrice, productFound.get().getPrice());

        repository.delete(result);

        assertThrows(NotFoundException.class, () -> repository.findById(result));
    }

    @Test
    void givenANullFilter_whenSearching_ShouldReturnTheProductFound() {
        for (var i = 0; i < 20; i++) {
            final var expectedName = UUID.randomUUID().toString();
            final var expectedDescription = UUID.randomUUID().toString();
            final var expectedCategory = UUID.randomUUID().toString();
            final var expectedPrice = 22.0;

            Product product = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);
            final var result = repository.insert(product);

            assertNotNull(result);
        }

        final GetAllProductOutput output = repository.findAll(10, 0, "name", "asc", null);

        assertNotNull(output);
        assertEquals(10, output.getProducts().size());
        assertEquals(20, output.getTotal());
    }

    @Test
    void givenAEmptyFilter_whenSearching_ShouldReturnTheProductFound() {
        for (var i = 0; i < 20; i++) {
            final var expectedName = UUID.randomUUID().toString();
            final var expectedDescription = UUID.randomUUID().toString();
            final var expectedCategory = UUID.randomUUID().toString();
            final var expectedPrice = 22.0;

            Product product = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);
            final var result = repository.insert(product);

            assertNotNull(result);
        }

        final GetAllProductOutput output = repository.findAll(10, 0, "name", "asc", "");

        assertNotNull(output);
        assertEquals(10, output.getProducts().size());
        assertEquals(20, output.getTotal());
    }

    @Test
    void givenAExactlyFilter_whenSearching_ShouldReturnTheProductFound() {
        final var expectedName1 = UUID.randomUUID().toString();
        final var expectedDescription1 = UUID.randomUUID().toString();
        final var expectedCategory1 = UUID.randomUUID().toString();
        final var expectedPrice1 = 22.0;

        Product product1 = getProduct(expectedName1, expectedDescription1, expectedCategory1, expectedPrice1);
        final var result1 = repository.insert(product1);

        assertNotNull(result1);

        for (var i = 0; i < 20; i++) {
            final var expectedName = UUID.randomUUID().toString();
            final var expectedDescription = UUID.randomUUID().toString();
            final var expectedCategory = UUID.randomUUID().toString();
            final var expectedPrice = 22.0;

            Product product = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);
            final var result = repository.insert(product);

            assertNotNull(result);
        }

        final GetAllProductOutput output = repository.findAll(10, 0, "name", "asc", expectedName1);

        assertNotNull(output);
        assertEquals(1, output.getProducts().size());
        assertEquals(1, output.getTotal());
    }

    @Test
    void givenAPartialFilter_whenSearching_ShouldReturnTheProductFound() {
        final var expectedName1 = "Processor " + UUID.randomUUID();
        final var expectedDescription1 = UUID.randomUUID().toString();
        final var expectedCategory1 = UUID.randomUUID().toString();
        final var expectedPrice1 = 22.0;

        Product product1 = getProduct(expectedName1, expectedDescription1, expectedCategory1, expectedPrice1);
        final var result1 = repository.insert(product1);

        assertNotNull(result1);

        for (var i = 0; i < 20; i++) {
            final var expectedName = UUID.randomUUID().toString();
            final var expectedDescription = UUID.randomUUID().toString();
            final var expectedCategory = UUID.randomUUID().toString();
            final var expectedPrice = 22.0;

            Product product = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);
            final var result = repository.insert(product);

            assertNotNull(result);
        }

        final GetAllProductOutput output = repository.findAll(10, 0, "name", "asc", "esso");

        assertNotNull(output);
        assertEquals(1, output.getProducts().size());
        assertEquals(1, output.getTotal());
    }

    @Test
    void givenAProductSameName_whenCallMethodIsThereAProductWithTheSameName_ShouldReturnTrue() {
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 22.0;

        Product product = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);
        final var result = repository.insert(product);

        assertNotNull(result);

        Product product2 = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exist = repository.isThereAProductWithTheSameName(product2);

        assertNotNull(exist);
        assertTrue(exist);

    }

    @Test
    void givenANewProduct_whenCallMethodIsThereAProductWithTheSameName_ShouldReturnFalse() {
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 22.0;
        Product product = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var result = repository.isThereAProductWithTheSameName(product);

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void givenAExistingProduct_whenCallMethodIsThereAProductWithTheSameName_ShouldReturnFalse() {
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 22.0;
        Product product = getProduct(expectedName, expectedDescription, expectedCategory, expectedPrice);
        final var result = repository.insert(product);
        assertNotNull(result);

        Optional<Product> insertProduct = repository.findById(result);

        final var exist = repository.isThereAProductWithTheSameName(insertProduct.get());
        assertFalse(exist);
    }


}
