package ru.ermakovis.service;

import org.springframework.data.domain.Page;
import ru.ermakovis.persist.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Page<Product> findByParams(Map<String, String> params);
    List<Product> findAll();
    Product findById(Integer id);
    void save(Product product);
    void deleteById(Integer id);
}
