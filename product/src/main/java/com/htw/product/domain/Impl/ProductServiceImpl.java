package com.htw.product.domain.Impl;

import com.htw.product.domain.ProductService;
import com.htw.product.error.ErrorResponseException;
import com.htw.product.model.Product;
import com.htw.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product createProduct(Product product) throws ErrorResponseException {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product updatedProduct) {
        // Implement validation or business logic if needed
        Product existingProduct = getProductById(productId);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setInventory(updatedProduct.getInventory());
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        // Implement validation or business logic if needed
        productRepository.deleteById(productId);
    }
}
