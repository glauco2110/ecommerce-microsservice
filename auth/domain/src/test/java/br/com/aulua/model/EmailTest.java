package br.com.aulua.model;

import br.com.aulua.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    @Test
    void givenAValidEmail_whenCallNewInstance_thenReturnInstanceEmail() {
        final var expectedEmail = "valid@email.com";
        final var email = Email.of(expectedEmail);

        assertNotNull(email);
        assertNotNull(email.getValue());
        assertEquals(expectedEmail, email.getValue());
    }

    @Test
    void givenAnInvalidEmail_whenCallNewInstance_thenThrowDomainException() {
        final var invalidEmail = "invalid-email";

        final var exception = assertThrows(DomainException.class, () -> Email.of(invalidEmail));

        assertEquals(DomainException.class, exception.getClass());
        assertEquals(1, exception.getErrors().size());
        assertEquals("email.invalid", exception.getErrors().getFirst());
    }

    @Test
    void givenAEmptyEmail_whenCallNewInstance_thenThrowDomainException() {
        final var emptyEmail = "";

        final var exception = assertThrows(DomainException.class, () -> Email.of(emptyEmail));

        assertEquals(DomainException.class, exception.getClass());
        assertEquals(1, exception.getErrors().size());
        assertEquals("email.invalid", exception.getErrors().getFirst());
    }

    @Test
    void givenAEmptySpaceEmail_whenCallNewInstance_thenThrowDomainException() {
        final var emptySpaceEmail = " ";

        final var exception = assertThrows(DomainException.class, () -> Email.of(emptySpaceEmail));

        assertEquals(DomainException.class, exception.getClass());
        assertEquals(1, exception.getErrors().size());
        assertEquals("email.invalid", exception.getErrors().getFirst());
    }

    @Test
    void givenANullableEmail_whenCallNewInstance_thenThrowDomainException() {
        final var exception = assertThrows(DomainException.class, () -> Email.of(null));

        assertEquals(DomainException.class, exception.getClass());
        assertEquals(1, exception.getErrors().size());
        assertEquals("email.invalid", exception.getErrors().getFirst());
    }
}
