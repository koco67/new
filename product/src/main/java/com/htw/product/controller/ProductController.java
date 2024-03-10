package com.htw.product.controller;

import com.htw.product.domain.ProductService;
import com.htw.product.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//controller soll in gateway für den frontend sein stattdessen listener
//rabbitmq producer in gateway der produziert ein event
//rabbitmq consumer statt controller
//remote prodecure call


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<Product>> getProductById(@PathVariable Long productId) {
        List<Product> product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }
}
