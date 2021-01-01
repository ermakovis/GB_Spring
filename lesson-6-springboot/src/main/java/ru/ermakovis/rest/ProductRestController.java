package ru.ermakovis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ermakovis.controllers.NotFoundException;
import ru.ermakovis.persist.entity.Product;
import ru.ermakovis.persist.repo.ProductRepository;

import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
public class ProductRestController {
    private final ProductRepository repository;

    @Autowired
    public ProductRestController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/get_all")
    public List<Product> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable("id") int id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        repository.save(product);
        return product;
    }

    @PutMapping
    public Product update(@RequestBody Product product) {
        if (repository.findById(product.getId()).isEmpty()) {
            throw new NotFoundException();
        }
        repository.save(product);
        return product;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        repository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException ex) {
        return new ResponseEntity<>("Not found." + ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
