package br.com.olua.output;

import br.com.olua.model.Product;
import lombok.Getter;

@Getter
public class GetProductOutput {
    private String id;
    private String name;
    private String description;
    private String category;
    private double price;

    private GetProductOutput(){}

    public static GetProductOutput from(Product product){
        if(product == null){
            return null;
        }
        GetProductOutput output = new GetProductOutput();
        output.id = product.getId();
        output.name = product.getName();
        output.description = product.getDescription();
        output.category = product.getCategory();
        output.price = product.getPrice();
        return output;
    }
}
