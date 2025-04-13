package br.com.aulua.model;

import br.com.aulua.exceptions.DomainException;
import lombok.Getter;

import java.util.List;

@Getter
public class Password {
    private String value;

    private Password() {}

    private Password(String value) {
        this.value = value;
    }

    public static Password of(String value) {
        if (!isValid(value)) {
            throw new DomainException(List.of("password.invalid"));
        }
        return new Password(value);
    }

    private static boolean isValid(String value) {
        if (value == null || value.length() < 8) {
            return false;
        }

        boolean hasUppercase = value.chars().anyMatch(Character::isUpperCase);
        boolean hasSymbol = value.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
        boolean hasNumber = value.chars().anyMatch(Character::isDigit);

        return hasUppercase && hasSymbol && hasNumber;
    }
}