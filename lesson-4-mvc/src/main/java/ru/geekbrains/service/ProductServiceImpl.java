package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.controllers.NotFoundException;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductSpecification;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Product> findByParams(Map<String, String> params) {
        Specification<Product> spec = Specification.where(null);
        String paramString;

        if ((paramString = params.get("minFilter")) != null && !paramString.isBlank()) {
            spec = spec.and(ProductSpecification.priceAfter(new BigDecimal(paramString)));
        }
        if ((paramString = params.get("maxFilter")) != null && !paramString.isBlank()) {
            spec = spec.and(ProductSpecification.priceBefore(new BigDecimal(paramString)));
        }

        //TODO get value from properties
        int pageSize = 5;
        int pageNumber = 1;
        if ((paramString = params.get("pageSize")) != null && !paramString.isBlank()) {
            pageSize = Integer.parseInt(paramString);
        }
        if ((paramString = params.get("pageNumber")) != null && !paramString.isBlank()) {
            pageNumber = Integer.parseInt(paramString);
        }
        String sortBy = params.getOrDefault("sortBy", "id");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy));
        return repository.findAll(spec, pageRequest);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public void save(Product product) {
        repository.save(product);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
