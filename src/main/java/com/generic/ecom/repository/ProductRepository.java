package com.generic.ecom.repository;

import com.generic.ecom.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    public Product save(Product product);
    public Product findProductByProductId(String productId);
}
