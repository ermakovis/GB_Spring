package ru.geekbrains.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductSpecification;

import java.math.BigDecimal;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String indexProductPage(Model model,
               @RequestParam(name = "minFilter", required = false) String minFilter,
               @RequestParam(name = "maxFilter", required = false) String maxFilter) {
        logger.info("Product page update");

        Specification<Product> spec = Specification.where(null);

        if (minFilter != null && !minFilter.isBlank()) {
            //В принципе можно обойти и встренными фильтрами JpaRepository
            //model.addAttribute("products", productRepository.findByPriceAfter(new BigDecimal(minFilter)));
            spec = spec.and(ProductSpecification.priceAfter(new BigDecimal(minFilter)));
        }
        if (maxFilter != null && !maxFilter.isBlank()) {
            spec = spec.and(ProductSpecification.priceBefore(new BigDecimal(maxFilter)));
        }

        model.addAttribute("products", productRepository.findAll(spec));
        return "product";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable(value = "id") Integer id, Model model) {
        logger.info("Edit product with id {}", id);
        model.addAttribute("product", productRepository.findById(id));
        return "product_form";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute(new Product());
        return "product_form";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) {
        productRepository.save(product);
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable(value = "id") Integer id) {
        logger.info("Delete product with id {}", id);
        productRepository.deleteById(id);
        return "redirect:/product";
    }
}
