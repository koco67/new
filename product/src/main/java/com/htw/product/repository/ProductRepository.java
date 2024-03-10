package com.htw.product.repository;

import com.htw.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Additional custom queries can be added here if needed

}
