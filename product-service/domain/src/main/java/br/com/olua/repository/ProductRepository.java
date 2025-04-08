package br.com.olua.repository;

import br.com.olua.model.Product;
import br.com.olua.output.GetAllProductOutput;

import java.util.Optional;

public interface ProductRepository {
    String insert(Product product);
    String update(Product product);
    void delete(String id);
    Optional<Product> findById(String id);
    GetAllProductOutput findAll(int limit, int offset, String sortBy, String sortOrder, String filter);

    boolean isThereAProductWithTheSameName(Product product);
}
