package br.com.olua.useCase;

import br.com.olua.exceptions.NotFoundException;
import br.com.olua.model.Product;
import br.com.olua.output.GetProductOutput;
import br.com.olua.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetProductUseCase implements UseCase<String, GetProductOutput> {

    private final ProductRepository repository;

    @Override
    public GetProductOutput execute(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new NotFoundException();
        }

        Optional<Product> product = repository.findById(id);

        if (product.isPresent()) {
            return GetProductOutput.from(product.get());
        }

        throw new NotFoundException();
    }
}
