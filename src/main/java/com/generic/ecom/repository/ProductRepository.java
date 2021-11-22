package com.generic.ecom.repository;

import com.generic.ecom.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    public Product save(Product product);
    public Product findProductByProductId(String productId);
}
