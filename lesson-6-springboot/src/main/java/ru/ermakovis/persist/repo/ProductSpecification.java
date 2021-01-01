package ru.ermakovis.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.ermakovis.persist.entity.Product;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<Product> priceAfter(BigDecimal min) {
        return (root, query, builder) -> builder.ge(root.get("price"), min);
    }

    public static Specification<Product> priceBefore(BigDecimal max) {
        return (root, query, builder) -> builder.le(root.get("price"), max);
    }
}
