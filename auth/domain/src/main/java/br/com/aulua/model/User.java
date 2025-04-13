package br.com.aulua.model;

import br.com.aulua.exceptions.DomainException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class User {

    private String id;
    private String name;
    private Email email;
    private Password password;

    private User() {}

    private User(String name, Email email, Password password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User of(String name, Email email, Password password) {
        List<String> validationErrors = validate(name, email, password);

        if (!validationErrors.isEmpty()) {
            throw new DomainException(validationErrors);
        }

        return new User(name, email, password);
    }

    private static List<String> validate(String name, Email email, Password password) {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isEmpty()) {
            errors.add("name.required");
        }

        if (email == null || email.getValue() == null || email.getValue().isEmpty()) {
            errors.add("email.required");
        }

        if (password == null || password.getValue() == null || password.getValue().isEmpty()) {
            errors.add("password.required");
        }

        return errors;
    }
}
