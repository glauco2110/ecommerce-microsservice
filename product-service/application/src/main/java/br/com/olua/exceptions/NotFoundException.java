package br.com.olua.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("domain.notfound");
    }

}
