package com.htw.product.domain;

import com.htw.product.error.ErrorResponseException;
import com.htw.product.model.Product;

import java.util.List;

public interface ProductService {
    
    List<Product> getAllProducts();

    List<Product> getProductById(Long productId);

    Product createProduct(Product product) throws ErrorResponseException;

    Product updateProduct(Long productId, Product updatedProduct)throws ErrorResponseException;

    void deleteProduct(Long productId)throws ErrorResponseException;

}
