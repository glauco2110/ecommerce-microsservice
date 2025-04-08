package br.com.olua.useCase;

public interface UseCase<INPUT, OUTPUT> {
    OUTPUT execute(INPUT input);
}
