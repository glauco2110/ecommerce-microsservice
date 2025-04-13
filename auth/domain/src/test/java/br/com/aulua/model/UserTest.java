package br.com.aulua.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    // Test cases for User class
    // 1. Test user creation with valid data
    // 2. Test user creation with invalid name
    // 3. Test user creation with invalid email
    // 4. Test user creation with invalid password
    // 5. Test user creation with null values

    // Add your test methods here

    @Test
    void givenAValidUser_whenCalledNewInstance_thenReturnNewUser() {
        final var expectedName = UUID.randomUUID().toString();
        final var expectedEmail = "jhon@doe.com";
        final var expectedPassword = "passworD123!";
        final var user =  User.of(expectedName, Email.of(expectedEmail), Password.of(expectedPassword));

        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getEmail().getValue());
        assertNotNull(user.getPassword().getValue());
        assertEquals(expectedName, user.getName());
        assertEquals(expectedEmail, user.getEmail().getValue());
        assertEquals(expectedPassword, user.getPassword().getValue());

    }
}
