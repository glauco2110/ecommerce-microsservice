package br.com.olua.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ApplicationException extends RuntimeException {
    private final List<String> errors;

    public ApplicationException(List<String> errors) {
        super("application.validation.error");
        this.errors = errors;
    }

}
