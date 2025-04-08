package br.com.olua.persistence.mapper;

import br.com.olua.model.Product;
import br.com.olua.persistence.data.ProductData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public Product domain(ProductData data) {
        if (data == null) return null;
        return Product.of(data.getId(), data.getName(),data.getDescription(), data.getCategory(), data.getPrice());
    }

    public List<Product> domain(List<ProductData> list) {
        if (list == null) return null;
        return list.stream().map(this::domain).toList();
    }

    public ProductData data(Product domain) {
        if (domain == null) return null;
        ProductData data = new ProductData();
        data.setId(domain.getId());
        data.setName(domain.getName());
        data.setCategory(domain.getCategory());
        data.setDescription(domain.getDescription());
        data.setPrice(domain.getPrice());
        return data;
    }
}
