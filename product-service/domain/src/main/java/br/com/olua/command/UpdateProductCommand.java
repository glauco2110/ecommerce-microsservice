package br.com.olua.command;

import br.com.olua.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProductCommand {

    private String id;
    private String name;
    private String description;
    private String category;
    private double price;

    public Product of() {
        return Product.of(id, name, description, category, price);
    }
}
