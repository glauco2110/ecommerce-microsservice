package br.com.olua.persistence.repository;

import br.com.olua.exceptions.NotFoundException;
import br.com.olua.model.Product;
import br.com.olua.output.GetAllProductOutput;
import br.com.olua.output.ProductListOutput;
import br.com.olua.persistence.data.ProductData;
import br.com.olua.persistence.mapper.ProductMapper;
import br.com.olua.persistence.repository.data.ProductDataRepository;
import br.com.olua.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductDataRepository repository;
    private final ProductMapper mapper;
    private final EntityManager em;

    @Override
    public String insert(Product product) {
        ProductData data = mapper.data(product);
        return repository.saveAndFlush(data).getId();
    }

    @Override
    public String update(Product product) {
        ProductData data = mapper.data(product);
        return repository.saveAndFlush(data).getId();
    }

    @Override
    public void delete(String id) {
        ProductData data = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.delete(data);
    }

    @Override
    public Optional<Product> findById(String id) {
        ProductData data = repository.findById(id).orElseThrow(NotFoundException::new);
        return Optional.of(mapper.domain(data));
    }

    private long count(String filter) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Long.class);
        var root = query.from(ProductData.class);

        if (filter != null && !filter.isBlank()) {
            query.where(cb.like(root.get("name"), "%" + filter + "%"));
        }

        return em.createQuery(query.select(cb.count(root)))
                .getSingleResult();
    }

    @Override
    public GetAllProductOutput findAll(int limit, int offset, String sortBy, String sortOrder, String filter) {

        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(ProductData.class);
        var root = query.from(ProductData.class);

        query.multiselect(
                root.get("id").alias("id"),
                root.get("name").alias("name"),
                root.get("price").alias("price"),
                root.get("description").alias("description"),
                root.get("category").alias("category")
        );

        if (filter != null && !filter.isBlank()) {
            query.where(cb.like(root.get("name"), "%" + filter + "%"));
        }

        var order = sortOrder.equals("asc") ? cb.asc(root.get(sortBy)) : cb.desc(root.get(sortBy));
        query.orderBy(order);

        var result = em.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        var total = this.count(filter);

        List<Product> products = mapper.domain(result);
        List<ProductListOutput> output = ProductListOutput.from(products);

        return GetAllProductOutput.of(output, total);
    }

    @Override
    public boolean isThereAProductWithTheSameName(Product product) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Long.class);
        var root = query.from(ProductData.class);

        List<Predicate> specs = new ArrayList<>();

        if (product != null && product.getId() != null) {
            specs.add(cb.notEqual(root.get("id"), product.getId()));
        }

        specs.add(cb.equal(root.get("name"), product.getName()));

        query.where(specs.toArray(new Predicate[specs.size()]));

        var total = em.createQuery(query.select(cb.count(root)))
                .getSingleResult();

        return total > 0;
    }
}
