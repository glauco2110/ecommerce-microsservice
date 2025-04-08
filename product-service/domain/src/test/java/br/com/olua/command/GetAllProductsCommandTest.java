package br.com.olua.command;

import br.com.olua.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GetAllProductsCommandTest {

    @Test
    void givenAValidCommand_whenInstantiated_thenNoExceptionIsThrown() {
        final var expectedLimit = 10;
        final var expectedOffset = 0;
        final var expectedSort = UUID.randomUUID().toString();
        final var expectedOrder = UUID.randomUUID().toString();
        final var expectedTerm = UUID.randomUUID().toString();

        final var command = new GetAllProductsCommand(expectedLimit, expectedOffset, expectedSort, expectedOrder, expectedTerm);

        assertEquals(expectedLimit, command.getLimit());
        assertEquals(expectedOffset, command.getOffset());
        assertEquals(expectedSort, command.getSort());
        assertEquals(expectedOrder, command.getOrder());
        assertEquals(expectedTerm, command.getTerm());

    }

    @Test
    void givenAnInvalidCommand_whenInstantiated_thenExceptionIsThrown() {
        final var expectedLimit = 0;
        final var expectedOffset = -1;
        final var expectedSort = "";
        final var expectedOrder = "";
        final var expectedTerm = "";

        final var exception = assertThrows(DomainException.class, () -> new GetAllProductsCommand(expectedLimit, expectedOffset, expectedSort, expectedOrder, expectedTerm));

        assertEquals(4, exception.getErrors().size());
        assertEquals("domain.validation.error", exception.getMessage());
        assertTrue(exception.getErrors().contains("domain.limit.invalid"));
        assertTrue(exception.getErrors().contains("domain.offset.invalid"));
        assertTrue(exception.getErrors().contains("domain.sort.invalid"));
        assertTrue(exception.getErrors().contains("domain.order.invalid"));
    }

    @Test
    void givenAnNullSort_whenInstantiated_thenExceptionIsThrown() {
        final var expectedLimit = 10;
        final var expectedOffset = 0;
        final var expectedOrder = UUID.randomUUID().toString();
        final var expectedTerm = UUID.randomUUID().toString();

        final var exception = assertThrows(DomainException.class, () -> new GetAllProductsCommand(expectedLimit, expectedOffset, null, expectedOrder, expectedTerm));

        assertEquals(1, exception.getErrors().size());
        assertEquals("domain.validation.error", exception.getMessage());
        assertTrue(exception.getErrors().contains("domain.sort.invalid"));
    }

    @Test
    void givenAnNullOrder_whenInstantiated_thenExceptionIsThrown() {
        final var expectedLimit = 10;
        final var expectedOffset = 0;
        final var expectedSort = UUID.randomUUID().toString();
        final var expectedTerm = UUID.randomUUID().toString();

        final var exception = assertThrows(DomainException.class, () -> new GetAllProductsCommand(expectedLimit, expectedOffset, expectedSort, null, expectedTerm));

        assertEquals(1, exception.getErrors().size());
        assertEquals("domain.validation.error", exception.getMessage());
        assertTrue(exception.getErrors().contains("domain.order.invalid"));
    }
}
