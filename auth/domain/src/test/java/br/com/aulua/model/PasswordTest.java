package br.com.aulua.model;

import br.com.aulua.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    @Test
    void givenAValidPassword_whenCallNewInstance_thenReturnInstancePassword() {
        final var expectedPassword = "validPassword123!";
        final var password = Password.of(expectedPassword);

        assertNotNull(password);
        assertNotNull(password.getValue());
        assertEquals(expectedPassword, password.getValue());
    }

    @Test
    void givenAnInvalidPassword_whenCallNewInstance_thenThrowDomainException() {
        final var invalidPassword = "short";

        final var exception = assertThrows(DomainException.class, () -> Password.of(invalidPassword));

        assertEquals(DomainException.class, exception.getClass());
        assertEquals(1, exception.getErrors().size());
        assertEquals("password.invalid", exception.getErrors().getFirst());
    }

    @Test
    void givenANullPassword_whenCallNewInstance_thenThrowDomainException() {
        final var exception = assertThrows(DomainException.class, () -> Password.of(null));

        assertEquals(DomainException.class, exception.getClass());
        assertEquals(1, exception.getErrors().size());
        assertEquals("password.invalid", exception.getErrors().getFirst());
    }

    @Test
    void givenAEmptyValue_whenCallNewInstance_thenThrowDomainException() {
        final var exception = assertThrows(DomainException.class, () -> Password.of(""));

        assertEquals(DomainException.class, exception.getClass());
        assertEquals(1, exception.getErrors().size());
        assertEquals("password.invalid", exception.getErrors().getFirst());
    }

    @Test
    void givenAEmptySpaceValue_whenCallNewInstance_thenThrowDomainException() {
        final var exception = assertThrows(DomainException.class, () -> Password.of("          "));

        assertEquals(DomainException.class, exception.getClass());
        assertEquals(1, exception.getErrors().size());
        assertEquals("password.invalid", exception.getErrors().getFirst());
    }

    @Test
    void givenAInvalidPasswordWithoutNumber_whenCallNewInstance_thenReturnInstancePassword() {
        final var expectedPassword = "validPasswordAbc!";

        final var exception = assertThrows(DomainException.class, () -> Password.of(expectedPassword));

        assertEquals(DomainException.class, exception.getClass());
        assertEquals(1, exception.getErrors().size());
        assertEquals("password.invalid", exception.getErrors().getFirst());
    }

    @Test
    void givenAInvalidPasswordWithoutSpecialCharacter_whenCallNewInstance_thenReturnInstancePassword() {
        final var expectedPassword = "validPasswordAbc56";

        final var exception = assertThrows(DomainException.class, () -> Password.of(expectedPassword));

        assertEquals(DomainException.class, exception.getClass());
        assertEquals(1, exception.getErrors().size());
        assertEquals("password.invalid", exception.getErrors().getFirst());
    }
}
