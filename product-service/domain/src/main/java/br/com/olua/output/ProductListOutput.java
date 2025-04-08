package br.com.olua.output;

import br.com.olua.model.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductListOutput {
    private String id;
    private String name;
    private double price;

    private ProductListOutput(){}

    public static List<ProductListOutput> from(List<Product> products){
        if(products == null){
            return new ArrayList<>();
        }

        List<ProductListOutput> output = new ArrayList<>();
        products.forEach(product -> output.add(ProductListOutput.from(product)));

        return output;
    }

    private static ProductListOutput from(Product product){
        ProductListOutput output = new ProductListOutput();
        output.id = product.getId();
        output.name = product.getName();
        output.price = product.getPrice();
        return output;
    }
}
