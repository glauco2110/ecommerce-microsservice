package br.com.olua.useCase;

import br.com.olua.command.CreateProductCommand;
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

@SpringBootTest(classes =  {CreateProductUseCase.class, ProductRepository.class})
public class CreateProductUseCaseTest {

    @MockitoBean
    private ProductRepository repository;

    private CreateProductUseCase useCase;

    @BeforeEach
    void setUp() {
        MockReset.before();
        useCase = new CreateProductUseCase(repository);
    }

    @Test
    void givenAValidProduct_whenExecute_thenSuccess() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        final var command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

        when(repository.isThereAProductWithTheSameName(any(Product.class))).thenReturn(false);
        when(repository.insert(any(Product.class))).thenReturn(expectedId);

        final var result = useCase.execute(command);

        assertNotNull(result);
        assertEquals(expectedId, result);

        verify(repository, times(1)).isThereAProductWithTheSameName(any(Product.class));
        verify(repository, times(1)).insert(any(Product.class));
    }

    @Test
    void givenAProductWithTheSameName_whenExecute_thenThrowException() {
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;
        final var expectedMessageException = "application.validation.error";
        final var expectedError = "product.name.already.exists";

        final var command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

        when(repository.isThereAProductWithTheSameName(any(Product.class))).thenReturn(true);

        final var exception = assertThrows(ApplicationException.class, () -> useCase.execute(command));


        assertEquals(expectedMessageException, exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains(expectedError));

        verify(repository, times(1)).isThereAProductWithTheSameName(any(Product.class));
        verify(repository, never()).insert(any(Product.class));
    }

    @Test
    void giverAProductWithoutName_whenExecute_thenThrowException() {
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;
        final var expectedMessageException = "domain.validation.error";
        final var expectedError = "product.name.required";

        final var command = new CreateProductCommand(null, expectedDescription, expectedCategory, expectedPrice);

        final var exception = assertThrows(DomainException.class, () -> useCase.execute(command));

        assertEquals(expectedMessageException, exception.getMessage());
        assertEquals(1, exception.getErrors().size());
        assertTrue(exception.getErrors().contains(expectedError));

        verify(repository, never()).isThereAProductWithTheSameName(any(Product.class));
        verify(repository, never()).insert(any(Product.class));
    }
}
