package com.htw.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htw.gateway.sender.ProductSender;

// ... existing imports

@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

    private final ProductSender productSender;

    @Autowired
    public GatewayController(ProductSender productSender) {
        this.productSender = productSender;
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        // Forward the request to the Product service via RabbitMQ
        Product product = new Product();
        product.setId(productId);
        product.setOperationType(OperationType.GET);

        productSender.sendMessage(product);

        // Return a placeholder response (you may modify this based on your needs)
        return ResponseEntity.ok(product);
    }
}
