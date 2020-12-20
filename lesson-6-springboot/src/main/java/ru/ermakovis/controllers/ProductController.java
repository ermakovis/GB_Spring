package ru.ermakovis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ermakovis.persist.entity.Product;
import ru.ermakovis.service.ProductService;

import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public String indexProductPage(Model model,
                   @RequestParam Map<String, String> params) {
        logger.info("Product page update, param size {}", params.size());
        model.addAttribute("products", productService.findByParams(params));
        return "product/main";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable(value = "id") Integer id, Model model) {
        logger.info("Edit product with id {}", id);
        model.addAttribute("product", productService.findById(id));
        return "product_form";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute(new Product());
        return "product_form";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) {
        productService.save(product);
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable(value = "id") Integer id) {
        logger.info("Delete product with id {}", id);
        productService.deleteById(id);
        return "redirect:/product";
    }

    @ExceptionHandler
    public String handleNotFoundException(NotFoundException ex) {
        logger.info("Not found exception occured");
        return "not_found";
    }
}
