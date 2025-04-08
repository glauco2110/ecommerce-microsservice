package br.com.olua.useCase;

import br.com.olua.command.UpdateProductCommand;
import br.com.olua.exceptions.ApplicationException;
import br.com.olua.model.Product;
import br.com.olua.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateProductUseCase implements UseCase<UpdateProductCommand, String> {

    private final ProductRepository repository;

    @Override
    public String execute(UpdateProductCommand command) {
        Product product = command.of();
        validate(product);
        return repository.update(product);
    }

    private void validate(Product product) {
        boolean isExists = repository.isThereAProductWithTheSameName(product);
        if (isExists) {
            throw new ApplicationException(List.of("product.name.already.exists"));
        }
    }
}
