package br.com.olua.useCase;

import br.com.olua.command.GetAllProductsCommand;
import br.com.olua.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockReset;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes =  {GetAllProductsUseCase.class, ProductRepository.class})
public class GetAllProductsUseCaseTest {

    @MockitoBean
    private ProductRepository repository;

    private GetAllProductsUseCase useCase;

    @BeforeEach
    void setUp() {
        MockReset.before();
        useCase = new GetAllProductsUseCase(repository);
    }


    @Test
    void givenAValidCommand_whenExecute_thenSuccess() {
        final var expectedTerm = UUID.randomUUID().toString();
        final var expectedPage = 0;
        final var expectedSize = 10;
        final var expectedSort = UUID.randomUUID().toString();
        final var expectedDirection = UUID.randomUUID().toString();

        GetAllProductsCommand command = new GetAllProductsCommand(expectedSize, expectedPage, expectedSort, expectedDirection, expectedTerm);
        useCase.execute(command);
        verify(repository, times(1)).findAll(expectedSize, expectedPage, expectedSort, expectedDirection, expectedTerm);
    }

}
