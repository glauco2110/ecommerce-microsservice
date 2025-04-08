package br.com.olua.rest;

import br.com.olua.command.CreateProductCommand;
import br.com.olua.command.GetAllProductsCommand;
import br.com.olua.command.UpdateProductCommand;
import br.com.olua.output.GetAllProductOutput;
import br.com.olua.output.GetProductOutput;
import br.com.olua.useCase.CreateProductUseCase;
import br.com.olua.useCase.GetAllProductsUseCase;
import br.com.olua.useCase.GetProductUseCase;
import br.com.olua.useCase.UpdateProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> insert(@RequestBody CreateProductCommand command) {
        String id = createProductUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody UpdateProductCommand command) {
        String id = updateProductUseCase.execute(command);
        return ResponseEntity.ok(id).status(HttpStatus.OK).build();
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetProductOutput> findById(@PathVariable String id) {
        GetProductOutput product = getProductUseCase.execute(id);
        return ResponseEntity.ok(product);
    }

    @Transactional(readOnly = true)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetAllProductOutput> findAll(@RequestParam(required = false) String filter,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(required = false) String sort,
                                                       @RequestParam(required = false) String order) {

        GetAllProductsCommand command = new GetAllProductsCommand(size, page, sort, order, filter);
        GetAllProductOutput products = getAllProductsUseCase.execute(command);

        return ResponseEntity.ok(products);
    }


}
