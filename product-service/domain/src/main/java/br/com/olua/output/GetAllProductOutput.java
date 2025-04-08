package br.com.olua.output;

import lombok.Getter;

import java.util.List;

@Getter
public class GetAllProductOutput {
    private List<ProductListOutput> products;
    private long total;

    private GetAllProductOutput() {}

    private GetAllProductOutput(List<ProductListOutput> products, long total) {
        this.products = products;
        this.total = total;
    }

    public static GetAllProductOutput of(List<ProductListOutput> products, long total) {
        return new GetAllProductOutput(products, total);
    }
}
