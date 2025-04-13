package br.com.aulua.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("domain.notfound");
    }

}
