package br.com.aulua.model;

import br.com.aulua.exceptions.DomainException;
import lombok.Getter;

import java.util.List;

@Getter
public class Email {

    private String value;

    private Email(){}

    private Email(final String value){
        this.value = value;
    }

    public static Email  of(String value) {
        if(!isValid(value)) {
            throw new DomainException(List.of("email.invalid"));
        }
        return new Email(value);
    }

    private static boolean isValid(String value) {
        return value != null && !value.isEmpty() && value.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

}
