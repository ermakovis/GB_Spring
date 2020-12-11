package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> nameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> priceAfter(BigDecimal min) {
        return (root, query, builder) -> builder.ge(root.get("price"), min);
    }

    public static Specification<Product> priceBefore(BigDecimal max) {
        return (root, query, builder) -> builder.le(root.get("price"), max);
    }
}
