package br.com.olua.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class DomainException extends RuntimeException {
    private final List<String> errors;

    public DomainException(List<String> errors) {
        super("domain.validation.error");
        this.errors = errors;
    }


    @Override
    public String toString() {
        return super.toString() + " Caused by: " + String.join(", ", errors);
    }

}
