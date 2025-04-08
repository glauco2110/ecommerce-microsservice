package br.com.olua.command;

import br.com.olua.exceptions.DomainException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetAllProductsCommand {
    private int limit;
    private int offset;
    private String sort;
    private String order;
    private String term;

    public GetAllProductsCommand(int limit, int offset, String sort, String order, String term) {
        List<String> validationErrors = validate(limit, offset, sort, order, term);
        if (!validationErrors.isEmpty()) {
            throw new DomainException(validationErrors);
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
        this.term = term;
    }

    public List<String> validate(int limit, int offset, String sort, String order, String term){
        List<String> errors = new ArrayList<>();

        if (limit <= 0) {
            errors.add("domain.limit.invalid");
        }

        if (offset < 0) {
            errors.add("domain.offset.invalid");
        }

        if (sort == null || sort.isBlank()) {
            errors.add("domain.sort.invalid");
        }

        if (order == null || order.isBlank()) {
            errors.add("domain.order.invalid");
        }

        return errors;
    }
}
