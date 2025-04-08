package br.com.olua.useCase;

import br.com.olua.command.GetAllProductsCommand;
import br.com.olua.output.GetAllProductOutput;
import br.com.olua.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllProductsUseCase implements UseCase<GetAllProductsCommand, GetAllProductOutput> {

    private final ProductRepository productRepository;

    @Override
    public GetAllProductOutput execute(GetAllProductsCommand command) {
        return productRepository.findAll( command.getLimit(), command.getOffset(), command.getSort(), command.getOrder(), command.getTerm());
    }
}
