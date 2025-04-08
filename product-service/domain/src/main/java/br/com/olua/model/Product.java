package br.com.olua.model;

import br.com.olua.exceptions.DomainException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Product {
    private String id;
    private String name;
    private String description;
    private String category;
    private double price;

    private Product(){}

    private Product(String id, String name, String description, String category, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public static Product of(String name, String description, String category, double price) {
        List<String> validationErrors = validate(name, description, category, price);
        if (!validationErrors.isEmpty()) {
            throw new DomainException(validationErrors);
        }
        return new Product(null, name, description, category, price);
    }

    public static Product of(String id, String name, String description, String category, double price) {
        List<String> validationErrors = validate(name, description, category, price);

        if (id == null || id.isBlank()) {
            validationErrors.add("product.id.required");
        }

        if (!validationErrors.isEmpty()) {
            throw new DomainException(validationErrors);
        }
        return new Product(id, name, description, category, price);
    }

    private static List<String> validate(String name, String description, String category, double price) {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank()) {
            errors.add("product.name.required");
        }
        if (description == null || description.isBlank()) {
            errors.add("product.description.required");
        }
        if (category == null || category.isBlank()) {
            errors.add("product.category.required");
        }
        if (price <= 0) {
            errors.add("product.price.invalid");
        }

        return errors;
    }
}
