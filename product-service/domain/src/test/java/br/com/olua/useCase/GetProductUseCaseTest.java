package br.com.olua.useCase;

import br.com.olua.exceptions.NotFoundException;
import br.com.olua.model.Product;
import br.com.olua.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockReset;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {GetProductUseCase.class, ProductRepository.class})
public class GetProductUseCaseTest {

    @MockitoBean
    private ProductRepository repository;

    private GetProductUseCase useCase;

    @BeforeEach
    void setUp() {
        MockReset.before();
        useCase = new GetProductUseCase(repository);
    }

    private Product getProduct(String id, String name, String description, String category, double price) {
        return Product.of(id, name, description, category, price);
    }

    @Test
    void givenAValidId_whenExecute_thenReturnGetProductOutput() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        Product mockedProduct = getProduct(expectedId, expectedName, expectedDescription, expectedCategory, expectedPrice);

        when(repository.findById(expectedId)).thenReturn(Optional.of(mockedProduct));

        final var output = useCase.execute(expectedId);

        verify(repository, times(1)).findById(expectedId);
        assertEquals(expectedId, output.getId());
        assertEquals(expectedName, output.getName());
        assertEquals(expectedDescription, output.getDescription());
        assertEquals(expectedCategory, output.getCategory());
        assertEquals(expectedPrice, output.getPrice());
    }

    @Test
    void givenANullId_whenExecute_thenThrowNotFoundException() {
        final String expectedId = null;

        when(repository.findById(expectedId)).thenReturn(Optional.empty());

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(expectedId));

        verify(repository, never()).findById(expectedId);
        assertEquals("domain.notfound", exception.getMessage());

    }

    @Test
    void givenAnEmptyId_whenExecute_thenThrowNotFoundException() {
        final String expectedId = "";

        when(repository.findById(expectedId)).thenReturn(Optional.empty());

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(expectedId));

        verify(repository, never()).findById(expectedId);
        assertEquals("domain.notfound", exception.getMessage());
    }

    @Test
    void givenAnInvalidId_whenExecute_thenThrowNotFoundException() {
        final String expectedId = "invalid-id";

        when(repository.findById(expectedId)).thenReturn(Optional.empty());
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(expectedId));

        verify(repository, times(1)).findById(expectedId);
        assertEquals("domain.notfound", exception.getMessage());
    }

}
