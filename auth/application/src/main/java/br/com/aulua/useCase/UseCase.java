package br.com.aulua.useCase;

public interface UseCase<INPUT, OUTPUT> {
    OUTPUT execute(INPUT input);
}
