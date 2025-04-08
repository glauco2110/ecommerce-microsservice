package br.com.olua.useCase;

import br.com.olua.command.UpdateProductCommand;
import br.com.olua.exceptions.ApplicationException;
import br.com.olua.exceptions.DomainException;
import br.com.olua.model.Product;
import br.com.olua.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockReset;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {UpdateProductUseCase.class, ProductRepository.class})
public class UpdateProductUseCaseTest {

    @MockitoBean
    private ProductRepository repository;

    private UpdateProductUseCase useCase;

    @BeforeEach
    void setUp() {
        MockReset.before();
        useCase = new UpdateProductUseCase(repository);
    }

    @Test
    void givenAValidProduct_whenExecute_thenSuccess() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        when(repository.isThereAProductWithTheSameName(any(Product.class))).thenReturn(false);
        when(repository.update(any(Product.class))).thenReturn(expectedId);

        final var result = useCase.execute(command);

        verify(repository, times(1)).update(any(Product.class));
        assertNotNull(result);
        assertEquals(expectedId, result);
    }


    @Test
    void givenAnEmptyProductName_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "";
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.name.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnNullProductName_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, null, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.name.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnEmptySpaceProductName_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "  ";
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.name.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnEmptyProductDescription_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = "";
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.description.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnNullProductDescription_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, null, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.description.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnEmptySpaceProductDescription_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = "  ";
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.description.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnEmptyProductCategory_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = "";
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.category.required", exceptions.getErrors().getFirst());
    }


    @Test
    void givenAnNullProductCategory_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, null, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.category.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnEmptySpaceProductCategory_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = "  ";
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.category.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnEmptyProductPrice_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 0.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.price.invalid", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnEmptyProductId_whenExecute_thenThrowException() {
        final var expectedId = "";
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.id.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnEmptySpaceProductId_whenExecute_thenThrowException() {
        final var expectedId = " ";
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.id.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAnNullProductId_whenExecute_thenThrowException() {
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        UpdateProductCommand command = new UpdateProductCommand(null, expectedName, expectedDescription, expectedCategory, expectedPrice);

        final var exceptions = assertThrows(DomainException.class, () -> useCase.execute(command));

        verify(repository, never()).update(any(Product.class));
        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        assertNotNull(exceptions);
        assertEquals(1, exceptions.getErrors().size());
        assertEquals("product.id.required", exceptions.getErrors().getFirst());
    }

    @Test
    void givenAProductWithTheSameName_whenExecute_thenThrowException() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;
        final var expectedMessageException = "application.validation.error";
        final var expectedError = "product.name.already.exists";

        final var command = new UpdateProductCommand(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        when(repository.isThereAProductWithTheSameName(any(Product.class))).thenReturn(true);

        final var exception = assertThrows(ApplicationException.class, () -> useCase.execute(command));

        assertEquals(expectedMessageException, exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains(expectedError));

        verify(repository, times(1)).isThereAProductWithTheSameName(any(Product.class));
        verify(repository, never()).update(any(Product.class));
    }
}
